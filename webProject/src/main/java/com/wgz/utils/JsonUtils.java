package com.wgz.utils;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import org.springframework.util.Assert;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

/**
 * json操作工具类
 * 
 * @author soulasuna
 *
 */
public class JsonUtils {
	
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
 
    /*--------------------business_method--------------------*/
    
	/**
	 * 对象转jsonString
	 * @param obj	转换对象
	 * @return		json字符串
	 */
	public static String toJson(Object obj) {
    	return gson.toJson(obj);
    }
	/**
     * json字符串转map
     * @param json      json字符串
     * @return          map集合
     */
    public static Map<String, Object> parseJsonToMap(String json){
        return gson.fromJson(json, new TypeToken<TreeMap<String, Object>>(){}.getType());
    }
	/**
	 * jsonString转对象
	 * @param json		json字符串
	 * @param clazz		要转化的字节码对象
	 * @return			转的对象
	 */
	public static <T> T parseJsonToBean(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }
	
    /**
     * map类型转化对象
     * @param map       map集合
     * @param clazz     对象的字节码文件
     * @param <T>       对象的泛型
     * @return          对象
     */
    public static <T> T conversionMapToBean(Map<String,Object> map, Class<T> clazz){
    	Assert.notNull(map, "error : conversion to bean map can not be null");
    	Assert.notNull(map, "error : conversion to bean clazz can not be null");
    	return parseJsonToBean(toJson(map),clazz);
    }
    
    /**
     * 对象类型转化map
     * @param bean		转换对象
     * @return			解析成map
     */
    public static Map<String,Object> conversionBeanToMap(Object bean){
    	Assert.notNull(bean, "error : conversion to map bean can not be null");
        return parseJsonToMap(toJson(bean));
    }
    
	
}
