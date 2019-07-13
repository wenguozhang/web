package com.wgz.utils;

import java.time.LocalDateTime;

/**
 * ProjectName: common
 * ClassName: IdGenerator
 * Date: 2019/1/16 13:21
 * Content: 唯一识别码生成器
 *
 * @author soulasuna
 * @version 1.0
 * @since JDK1.8
 */
public final class IdGenerator {
	private static String DATE_BATCH_PREFIX = "yyMMddHHmmss";
	
    /**
     * 基于雪花算法分布式id生成方法
     * 返回一个自增唯一的Long类型的id
     *
     * @return      自增19位long类型的id
     */
    public static Long generatorId(){
        return SnowflakeIdWorker.generateId();
    }
    /**
     * 基于雪花算法分布式id生成方法
     * 返回一个自增唯一的32位String类型的id
     * @return		自增32位String类型批次号
     */
    public static String generatorBatchNo(){
    	return DateUtils.formatLocalDateTimeToString(LocalDateTime.now(),DATE_BATCH_PREFIX)
    			+ SnowflakeIdWorker.generateId();
    }
    
    public static void main(String[] args) {
    	System.out.println(generatorId());
    	System.out.println(Long.MAX_VALUE);
    }
}
