package com.wgz.cache;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

public class RedisScriptLua {

	/**
	 * REDIS_LUA_TPS
	 * 1. key [1] {KEYS[1] 检索的key}
	 * 2. args [2] {ARGV[1] 间隔时间 , ARGV[2] tps}
	 */
	public final static String REDIS_LUA_TPS = "local current = redis.call('incr',KEYS[1]) if tonumber(current) == 1 then redis.call('expire',KEYS[1],tonumber(ARGV[1])) end if tonumber(current) > tonumber(ARGV[2]) then return -1 else return 1 end";
	
	/**    
	 * @Description: 加载lua脚本
	 */ 
	public String getLuaScript(String file) throws Exception {
		System.out.println("加载lua脚本开始");
//		String file = "/com/wgz/cache/lua/REDIS_LUA_TPS.lua";
		try(InputStream is = RedisTest.class.getResourceAsStream(file); ByteArrayOutputStream os = new ByteArrayOutputStream()){
			IOUtils.copy(is, os);
			String rs = os.toString("UTF-8");
//			System.out.println(rs);
			System.out.println("加载lua脚本结束");
			return rs;
		}catch (Exception e) {
			throw new Exception("加载lua脚本失败");
		}
		
	}
}
