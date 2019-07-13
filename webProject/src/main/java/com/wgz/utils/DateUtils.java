package com.wgz.utils;

import java.lang.reflect.Method;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

/**
 * ClassName: DateUtils
 * Date: 2018/12/29 10:34
 * Content: 时间方法工具类
 *          阿里开发手册:
 *              JDK8应用api更新
 *              Instant 代替 Date
 *              LocalDateTime代替Calendar，
 *              DateTimeFormatter代替SimpleDateFormat
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
public final class DateUtils {

    /*--------------------static_filed--------------------*/

    private static String [] PATTERN_STRING = {
            "yyyy-MM-dd HH:mm:ss",
            "yyyy/MM/dd HH:mm:ss",
            "yyyyMMddHHmmss",
            "yyMMddHHmmss",
            "yyyy-MM-dd",
            "yyyy/MM/dd",
            "yyyyMMdd",
            "yyMMdd",
            "HH:mm:ss",
            "HHmmss"
    };

    /*--------------------business_method--------------------*/

    /**
     * 获得系统当前的时间戳(毫秒级别)
     * 替换System.currentTimeMillis()方法
     * @return  当前的时间戳
     */
    public static Long currentTimeMillis(){
        return Instant.now().toEpochMilli();
    }

    /**
     * 获得当前时间的date对象
     * @return  当前时间对象
     */
    public static Date systemNowDate (){
        return Date.from(Instant.now());
    }
    
    /**    
     * @Description: 转化日期格式
     */ 
    public static LocalDateTime parseLocalDateTime(String dateTimeStr, String pattern) {
    	 if(!ArrayUtils.contains(PATTERN_STRING, pattern)) {
             throw new RuntimeException("error : date util format pattern not find");
         }
         DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return LocalDateTime.parse(dateTimeStr, formatter);
    }
    
    /**
     * 格式化时间字符串
     * @param localDateTime 当前的时间戳
     * @param pattern       格式化样式
     * @return              格式化以后的字符串
     */
    public static String formatLocalDateTimeToString(LocalDateTime localDateTime, String pattern){
        if(!ArrayUtils.contains(PATTERN_STRING, pattern)) {
            throw new RuntimeException("error : date util format pattern not find");
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        return formatter.format(localDateTime);
    }
    
    private String getDate(Object value) { 
    	Timestamp timestamp = null; 
    	try { 
    		timestamp = (Timestamp) value; 
    	} catch (Exception e) { 
    		timestamp = getOracleTimestamp(value); 
    	} 
    	if(timestamp!=null) 
    		return (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(timestamp); 
    	else 
    		return null; 
    } 
	/** 
     * @reference oracle.sql.Datum.timestampValue(); 
     * @return 
     */ 
	private Timestamp getOracleTimestamp(Object value) { 
		try { 
			Class clz = value.getClass(); 
			Method m = clz.getMethod("timestampValue", null); 
	       //m = clz.getMethod("timeValue", null); 时间类型 
	       //m = clz.getMethod("dateValue", null); 日期类型 
			return (Timestamp) m.invoke(value, null); 
		} catch (Exception e) { 
			return null; 
		} 
	}


    /*--------------------test_main--------------------*/

    public static void main(String[] args) {
        LocalDateTime now = LocalDateTime.now();
        System.out.println(formatLocalDateTimeToString(now,"yyyy-MM-dd"));

    }

}
