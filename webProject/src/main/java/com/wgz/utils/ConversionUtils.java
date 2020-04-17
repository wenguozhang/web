package com.wgz.utils;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * ProjectName: sltas_customer_client
 * ClassName: ConversionUtils
 * Date: 2018/11/13 15:28
 * Content: 利用gson作为中间件,实现map和对象的转换
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
@Slf4j
public class ConversionUtils {

    /*--------------------constant_param-----------------------------*/

    private static Gson gson;

    /**
     * 处理json转map的时候
     * 整形类型转变为浮点类型
     */
    static{
        gson = new GsonBuilder()
                .registerTypeAdapter(
                new TypeToken<TreeMap<String, Object>>(){}.getType(),
                new JsonDeserializer<TreeMap<String, Object>>(){
                    @Override
                    public TreeMap<String, Object> deserialize(JsonElement json, Type typeOfT,
                                                               JsonDeserializationContext context) throws JsonParseException {
                        TreeMap<String, Object> treeMap = new TreeMap<>();
                        JsonObject jsonObject = json.getAsJsonObject();
                        Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
                        for (Map.Entry<String, JsonElement> entry : entrySet) {
                            treeMap.put(entry.getKey(), entry.getValue());
                        }
                        return treeMap;
                    }
                }
        ).create();
    }

    /*--------------------business_method----------------------------*/

    /**
     * map类型转化对象
     * @param map       map集合
     * @param clazz     对象的字节码文件
     * @param <T>       对象的泛型
     * @return          对象
     */
    public static <T> T conversionMapToBean(Map<String,Object> map, Class<T> clazz){
       if(FunUtils.isNull(map) || FunUtils.isNull(clazz)){
            return null;
       }
       return parseJsonToBean(toJson(map),clazz);
    }

    /**
     * 对象类型转map
     * @param obj   对象
     * @return      装换的map
     */
    public static Map<String,Object> conversionBeanToMap(Object obj){
        if(FunUtils.isNull(obj)){
            return null;
        }
        return parseJsonToMap(toJson(obj));
    }

    /**
     *
     * @param fieldNames    字段名称数组
     * @param t             对象
     * @param tClass        对象字节码文件
     * @return              dataMap
     */
    public static <T> Map<String,Object> conversionDate(String[] fieldNames, T t, Class<?> tClass) {
        if(FunUtils.isNull(fieldNames) || FunUtils.isNull(t) || FunUtils.isNull(tClass)){
            throw new RuntimeException("error : param can not be null");
        }
        int length = fieldNames.length;
        Map<String,Object> resultMap = new HashMap<>(length);
        for (int i = 0; i < length; i++) {
            String key = fieldNames[i];
//            if(CommonConstant.MAP_KEY_TRADE_TIME.equals(key)){
//                resultMap.put(key, DateUtils.getTradeTime(LocalDateTime.now()));
//                continue;
//            }

            String methodName = getGetMethodNameByFiledName(key);
            Method method = getStringValueByGetMethodName(methodName, tClass);
            Object value = null;
            try {
                value = method.invoke(t,null);
                if(value instanceof BigDecimal){
                    value = value.toString();
                }
                if(FunUtils.isNull(value)){
                    value = "";
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            resultMap.put(key, value);
        }
        return resultMap;
    }

    /**
     * 根据字段名称获得set方法的名称
     * @param filedName     字段名称
     * @return              set方法的名称
     */
    public static String getSetMethodNameByFiledName(String filedName){
        if(FunUtils.isNull(filedName)){
            throw new RuntimeException("error : filed name can be not null");
        }
        StringBuilder sb = new StringBuilder("set");
        sb.append(filedName.substring(0,1).toUpperCase()).append(filedName.substring(1));
        return sb.toString();
    }

    /**
     * 根据字段名称获得get方法的名称
     * @param filedName     字段名称
     * @return              get方法名称
     */
    public static String getGetMethodNameByFiledName(String filedName){
        if(FunUtils.isNull(filedName)){
            throw new RuntimeException("error : filed name can be not null");
        }
        StringBuilder sb = new StringBuilder("get");
        sb.append(filedName.substring(0,1).toUpperCase()).append(filedName.substring(1));
        return sb.toString();
    }

    /**
     * 根据字段set方法的名称获得字段字符串类型的值
     * @param setMethodName     set方法的名称
     * @return                  set方法的对象
     */
    public static Method getStringParamBySetMethodName(String setMethodName, Class<?> clazz){
        if(FunUtils.isNull(setMethodName)){
            throw new RuntimeException("error : method name can be not null");
        }
        try {
            return clazz.getMethod(setMethodName, String.class);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据字段get方法的名称获得方法对象
     * @param getMethodName     get方法的名称
     * @return                  get方法的对象
     */
    public static <T> Method getStringValueByGetMethodName(String getMethodName, Class<T> clazz){
        if(FunUtils.isNull(getMethodName)){
            throw new RuntimeException("error : method name can be not null");
        }
        try {
            Method method = clazz.getMethod(getMethodName, null);
            return method;
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*--------------------------tools_method-------------------------*/

    /**
     * 对象转换成json
     * @param obj   对象
     * @return      json字符串
     */
    public static String toJson(Object obj) {
        return gson.toJson(obj);
    }

    /**
     * json字符串转成对象
     * @param json  json字符串
     * @param clazz 类的字节码文件
     * @param <T>   类的泛型
     * @return      对象
     */
    public static <T> T parseJsonToBean(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    /**
     * json字符串转map
     * @param json      json字符串
     * @return          map集合
     */
    public static Map<String, Object> parseJsonToMap(String json){
        return gson.fromJson(json, new TypeToken<TreeMap<String, Object>>(){}.getType());
    }
}
