package com.wgz.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

/**
 * ProjectName: sltas_customer_client
 * ClassName: PropertiesUtil
 * Date: 2018/11/7 17:00
 * Content: 加载properties文件工具类
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
@Slf4j
public class PropertiesUtil {

    /*--------------------static_param--------------------*/

    private static final Map<String, Properties> PROPERTIES_MAP = new HashMap<>();

    /*--------------------business_method--------------------*/

    /**
     * 根据Key获得properties对象
     *
     * @param propertiesName
     * @return
     */
    public static Properties getPropertiesByKey(String propertiesName){
        Properties props = PROPERTIES_MAP.get(propertiesName);
        if (FunUtils.isNull(props)) {
            loadProps(propertiesName);
        }
        return PROPERTIES_MAP.get(propertiesName);
    }

    /**
     * 获得全部的properties文件中全部key的值
     *
     * @return	key值set集合
     */
    public static Set<String> getPropertiesKeys(String propertiesName){
        loadProps(propertiesName);
        Properties props = PROPERTIES_MAP.get(propertiesName);
        return props.stringPropertyNames();
    }

    /**
     * 通过key获得value
     * @param key	key值
     * @return		key对应的value
     */
    public static String getValueByKey(String propertiesName, String key){
        if(FunUtils.isNull(key)) {
            throw new RuntimeException("error: Properties key must not be empty");
        }
        Properties props = getPropertiesByKey(propertiesName);
        return props.getProperty(key);
    }

    /*--------------------tools_method--------------------*/

    /**
     * 加载指定的文件初始化Properties对象
     * @param propertiesName	properties文件的名称
     */
    private synchronized static void loadProps(String propertiesName) {
        log.info("开始加载properties文件:{}",propertiesName);
        Properties props = PROPERTIES_MAP.get(propertiesName);
        if (FunUtils.isNull(props)) {
            props = new Properties();
        }
        InputStream in = null;
        try {
            in = PropertiesUtil.class.getClassLoader().getResourceAsStream(propertiesName);
            props.load(in);
            PROPERTIES_MAP.put(propertiesName, props);
        } catch (FileNotFoundException e) {
            log.error("error: {} properties file can not find ",propertiesName);
        } catch (IOException e) {
            log.error("error: {} properties file can not load ");
        } finally {
            try {
                if(null != in) {
                    in.close();
                }
            } catch (IOException e) {
                log.error("{}文件流关闭出现异常!!!",propertiesName);
            }
        }
    }
}
