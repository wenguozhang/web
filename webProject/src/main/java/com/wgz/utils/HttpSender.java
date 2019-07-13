package com.wgz.utils;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
/**
 * 发送HTTP请求
 * @author caiyonggang
 * @createDate 2016年8月2日上午11:09:37
 */
@Slf4j
public class HttpSender {
	private static final HttpSender instance = new HttpSender();
	private static HttpClientBuilder hcb = null;
	
	private HttpSender() {}
	
	static {
		//采用绕过验证的方式处理https请求
		SSLContext sslcontext = null;
		try {
			sslcontext = createIgnoreVerifySSL();
		} catch (Exception e) {
			log.error("", e);
		}
        // 设置协议http和https对应的处理socket链接工厂的对象
		SSLConnectionSocketFactory sSLConnectionSocketFactory = new SSLConnectionSocketFactory(sslcontext,SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
            .register("http", PlainConnectionSocketFactory.INSTANCE)
            .register("https", sSLConnectionSocketFactory)
            .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        hcb = HttpClients.custom().setConnectionManager(connManager);
	}

	public static HttpSender getInstance() {
		return instance;
	}

	/**
	 * post请求json数据进行交互
	 * @param url		请求url
	 * @param jsonData	json请求数据
	 * @return
	 * @throws IOException	链接异常
	 */
	public static Map<String, String> sendAndReciveJSON_1(String url,String jsonData) throws IOException {
		Map<String, String> res = null;
		try {
			log.info("====发送请求开始====url:{},请求数据jsonData:{}",url,jsonData);
			res = sendPost(url, jsonData);
		} catch (IOException e) {
			log.info("e{}",e);
			throw e;
		} catch (Exception e) {
			log.info("e{}",e);
		}
		return res;
	}

	/**
	 * post请求json数据进行交互
	 * @param url		请求url
	 * @param jsonData	json请求数据
	 * @return
	 * @throws IOException	链接异常
	 */
	public static JSONObject sendAndReciveJSON(String url,String jsonData) throws IOException {
		// 固定数据添加
		JSONObject obj  = null;
		try {
			log.info("====发送请求开始====url:{},请求数据jsonData:{}",url,jsonData);
			Map<String, String> res = sendPost(url, jsonData);
			String bodyStr = res.get("body");
			String status = res.get("status");
			//请求发送成功才能返回结果
			if("1".equals(status)){
				obj = JSONObject.fromObject(bodyStr);
			}
		} catch (IOException e) {
			log.info("e{}",e);
			throw e;
		} catch (Exception e) {
			log.info("e{}",e);
		}
		return obj;
	}


	/**
	 * 发送文本POST请求
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:41:16
	 * @param url
	 * @param strParam
	 * @return
	 * Map<String,String>： 
	 * 	元素1 "status":"状态：1成功（HttpStatus=200），0失败";
	 * 	元素2 "body":"返回信息"
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	public static Map<String,String> sendPost(String url,String strParam) throws Exception {
		Map<String,String> resultMap;
		HttpPost post = createStringPost(url,strParam);
		resultMap = sendHttpPost(post);
	    return resultMap;
	}
	
	/**
	 * 传入HttpPost对象，发送post请求，返回结果
	 * @author caiyonggang
	 * @createDate 2016年7月29日下午12:07:56
	 * @param post HttpPost对象
	 * @return 
	 * 	Map<String,String>： 
	 * 	元素1 "status":"状态：1成功（HttpStatus=200），0失败";
	 * 	元素2 "body":"返回信息"
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	private static Map<String,String> sendHttpPost(HttpPost post) throws Exception {
		String body;
		String status = "0";
		Map<String,String> resultMap = new HashMap<>(16);

		CloseableHttpClient httpClient = hcb.build();
		long startTime = System.currentTimeMillis();
		CloseableHttpResponse response = null;
		int statusCode;
		long endTime;
		try {
			response = httpClient.execute(post);

			statusCode = response.getStatusLine().getStatusCode();
		    if (statusCode == HttpStatus.SC_OK) {
		        status = "1";
		    }else{
		    	log.info("====请求失败====:" + response.getStatusLine());
		    }
		    HttpEntity entity = response.getEntity();
		    body = EntityUtils.toString(entity);
		    EntityUtils.consume(entity);
		}finally{
			endTime = System.currentTimeMillis();
			resultMap.put("status", status);
			response.close();
		}
		log.info("请求结果 statusCode:{};耗时:{}毫秒 ; 结果正文:{}",statusCode,(endTime - startTime),body);
	    resultMap.put("body", body);
		log.info("请求结果resultMap:{}",resultMap);
	    return resultMap;
	}
	
	/**
	 * 创建表单形式的POST请求对象
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:15:43
	 * @param url
	 * @param strParams 表单字段名值对：Map<String,String>
	 * @return
	 */
	private static HttpPost createFormPost(String url,Map<String,String> strParams) {
		HttpPost post = new HttpPost(url);
		// 对content做处理
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for(Entry<String,String> map :strParams.entrySet()){
			list.add(new BasicNameValuePair(map.getKey(),map.getValue()));
		}
		try {
			post.setEntity(new UrlEncodedFormEntity(list, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			// 这个错误可以忽略
		}
		return post;
	}
	
	/**
	 * 创建文本形式（JSON）的Post请求对象
	 * @author caiyonggang
	 * @createDate 2016年8月2日上午10:13:42
	 * @param url 请求地址
	 * @param strParam 请求文本（JSON）
	 * @return
	 */
	private static HttpPost createStringPost(String url,String strParam){
		HttpPost post = new HttpPost(url);
		post.addHeader("Content-type","application/json;charset=UTF-8");
	    post.setEntity(new StringEntity(strParam, Charset.forName("UTF-8")));
		return post;
	}
	
	/**
	 * SSLContext绕过验证
	 * 	
	 * @return
	 * @throws NoSuchAlgorithmException 
	 * @throws KeyManagementException 
	 */
	private static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
		SSLContext sc = SSLContext.getInstance("TLS");

		// 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
		X509TrustManager trustManager = new X509TrustManager() {
			@Override
			public void checkClientTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public void checkServerTrusted(
					java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
					String paramString) throws CertificateException {
			}

			@Override
			public java.security.cert.X509Certificate[] getAcceptedIssuers() {
				return null;
			}
		};
		sc.init(null, new TrustManager[] { trustManager }, null);
		return sc;
	}
}
