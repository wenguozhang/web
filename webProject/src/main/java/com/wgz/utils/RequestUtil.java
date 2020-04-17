package com.wgz.utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * ProjectName: sltas_customer_client
 * ClassName: RequestUtil
 * Date: 2018/11/7 10:31
 * Content: 解析请求对象工具类
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
@Slf4j
public class RequestUtil {

    /*--------------------static_filed--------------------*/

    /**
     * 解析请求对象
     * @param request   请求对象
     * @return          请求参数map
     */
    public static Map<String, Object> getParams(HttpServletRequest request) {
        Map<String, Object> reqParams = new HashMap<>(16);
        BufferedReader reader = null;
        try {
            StringBuffer sb = new StringBuffer();
            reader = request.getReader();
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            if(sb.length() > 0){
                reqParams =  jsonToMap(JSONObject.fromObject(sb.toString()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != reader){
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        log.info("====接收请求,入参requestMap:{}",reqParams);
        return reqParams;
    }

    /**
     * json装map
     * @param jsonObject    json对象
     * @return              参数map
     */
    public static Map<String, Object> jsonToMap(JSONObject jsonObject){
        Map<String, Object> map = new HashMap<>(16);
        Iterator<?> it = jsonObject.keys();
        while (it.hasNext()){
            String key = String.valueOf(it.next());
            Object value = jsonObject.get(key);
            if (value instanceof JSONObject) {
                map.put(key, jsonToMap((JSONObject) value));
            } else if(value instanceof JSONArray){
                List<Map<String, Object>> list = new ArrayList<>();
                Object[] objs = ((JSONArray) value).toArray();
                for (Object obj : objs) {
                    list.add(jsonToMap(JSONObject.fromObject(obj)));
                }
                map.put(key, list);
            }else{
                map.put(key, value);
            }
        }
        return map;
    }

}
