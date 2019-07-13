package com.wgz.utils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;

/**
 * ProjectName: workspace
 * ClassName: HttpClientOperate
 * Date: 2018/9/19 16:02
 * Content: 发送http请求的类
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
@Slf4j
@Component(value = "httpClientOperate")
public class HttpClientOperate implements BeanFactoryAware {

    private BeanFactory beanFactory;

    /**
     * 将required设置为false:
     * @Autowired(required=false):
     * 在找不到匹配bean进行注入时也不报错
     */
    @Autowired(required=false)
    private RequestConfig requestConfig;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * 在工厂类中获得httpclient对象
     * @return      httpclient对象
     */
    private CloseableHttpClient getHttpClient(){
        return this.beanFactory.getBean(CloseableHttpClient.class);
    }
    
    /**
     * 无参数的get请求
     * @param url           get请求的url
     * @return              响应的字符串
     * @throws IOException  链接异常
     */
    public String doGet(String url) throws IOException{
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        Integer statusCode ;
        String result = null;
        Long startTime;
        Long endTime;
        try {
            log.info("==get请求开始==请求url:{}",url);
            startTime = System.currentTimeMillis();
            response = this.getHttpClient().execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode.equals(HttpStatus.SC_OK)){
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        endTime = System.currentTimeMillis();
        log.info("==get请求结束==消耗时间:{}毫秒==响应状态码code:{}==响应结果result:{}==",(endTime-startTime),statusCode,result);
        return result;
    }

    /**
     * 无参数的get请求
     * @param url           get请求的url
     * @return              响应的json字符串
     * @throws IOException  链接异常
     */
    public JSONObject doGetJson(String url) throws IOException{
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(requestConfig);
        CloseableHttpResponse response = null;
        Integer statusCode ;
        JSONObject resultJson = null;
        String result = null;
        Long startTime;
        Long endTime;
        try {
            log.info("==get请求开始==请求url:{}",url);
            startTime = System.currentTimeMillis();
            response = this.getHttpClient().execute(httpGet);
            statusCode = response.getStatusLine().getStatusCode();
            if (statusCode.equals(HttpStatus.SC_OK)){
                result = EntityUtils.toString(response.getEntity(), "UTF-8");
                resultJson = JSONObject.fromObject(result);
            }
        } finally {
            if (response != null) {
                response.close();
            }
        }
        endTime = System.currentTimeMillis();
        log.info("==get请求结束==消耗时间:{}毫秒==响应状态码code:{}==响应结果result:{}==",(endTime-startTime),statusCode,result);
        return resultJson;
    }

    /**
     * 带有参数get请求
     * @param url           请求url
     * @param params        请求参数map
     * @return              响应字符串
     * @throws IOException  链接异常
     */
    public String doGet(String url , Map<String, String> params) throws IOException{
        URIBuilder uriBuilder;
        try {
            uriBuilder = new URIBuilder(url);
            if(params != null){
                for(String key : params.keySet()){
                    uriBuilder.setParameter(key, params.get(key));
                }
            }
            url = uriBuilder.build().toString();
            return this.doGet(url);
        }catch (URISyntaxException e) {
            log.info("==get请求拼接url异常==");
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 有参数的post请求
     * @param url           请求的地址
     * @param params        请求参数
     * @return              返回map
     * @throws IOException  链接异常
     */
    public Map<String,String> doPost(String url , Map<String, Object> params) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if(!FunUtils.isNull(params)){
            List<NameValuePair> parameters = new ArrayList<>(0);
            for(String key : params.keySet()){
                parameters.add(new BasicNameValuePair(key, FunUtils.formatString(params.get(key))));
            }
            UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters);
            httpPost.setEntity(formEntity);
        }
        CloseableHttpResponse response = null;
        Map<String,String> resultMap = null;
        Integer statusCode;
        String body;
        Long startTime;
        Long endTime;
        try{
            log.info("==post请求开始==请求url:{}==请求参数requestMap:{}",url,params);
            startTime = System.currentTimeMillis();
            response = this.getHttpClient().execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity(), "UTF-8");
            if(statusCode.equals(HttpStatus.SC_OK)) {
                resultMap = new HashMap<>(2);
                resultMap.put("code", String.valueOf(statusCode));
                resultMap.put("body", body);
            }
        }finally {
            if (response != null) {
                response.close();
            }
        }
        endTime = System.currentTimeMillis();
        log.info("==get请求结束==消耗时间:{}毫秒==响应状态码code:{}==响应结果result:{}==",(endTime-startTime),statusCode,body);
        return resultMap;
    }

    /**
     * json交互方式有参数的post请求
     * @param url           请求地址
     * @param jsonStr       请求参数的json字符串
     * @return              返回json对象
     * @throws IOException  链接异常
     */
    public JSONObject doPostJson(String url , String jsonStr) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setConfig(requestConfig);
        if (!FunUtils.isNull(jsonStr)){
            StringEntity stringEntity = new StringEntity(jsonStr, ContentType.APPLICATION_JSON);
            httpPost.setEntity(stringEntity);
        }
        CloseableHttpResponse response = null;
        JSONObject resultJson = null;
        Integer statusCode;
        String body;
        Long startTime;
        Long endTime;
        try{
            log.info("==post请求开始==请求url:{}==请求参数jsonStr:{}",url,jsonStr);
            startTime = System.currentTimeMillis();
            response = this.getHttpClient().execute(httpPost);
            statusCode = response.getStatusLine().getStatusCode();
            body = EntityUtils.toString(response.getEntity(), "UTF-8");
            if(statusCode.equals(HttpStatus.SC_OK)){
                resultJson = JSONObject.fromObject(body);
            }
        }finally{
            if (response != null) {
                response.close();
            }
        }
        endTime = System.currentTimeMillis();
        log.info("==get请求结束==消耗时间:{}毫秒==响应状态码code:{}==响应结果resultJson:{}==",(endTime-startTime),statusCode,resultJson);
        return resultJson;
    }

    /**
     * 没有参数的post请求
     * @param url           请求地址
     * @return              响应map
     * @throws IOException  链接异常
     */
    public Map<String,String> doPost(String url) throws IOException{
        return this.doPost(url, null);
    }

}
