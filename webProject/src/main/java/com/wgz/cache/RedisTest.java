package com.wgz.cache;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import javax.annotation.PostConstruct;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service("redisTest")
public class RedisTest {
	@Autowired
    @Qualifier(value = "redisService")
    private RedisService redisService;
	
	public static void main(String[] args) throws Exception {
		System.out.println("加载lua脚本开始");
		String file = "/com/wgz/cache/lua/REDIS_LUA_TPS.lua";
		try(InputStream is = RedisTest.class.getResourceAsStream(file); ByteArrayOutputStream os = new ByteArrayOutputStream()){
			IOUtils.copy(is, os);
			String rs = os.toString("UTF-8");
			System.out.println(rs);
		}catch (Exception e) {
			throw new Exception("加载lua脚本失败");
		}
		
		System.out.println("加载lua脚本结束");
	}
	
	@PostConstruct
	public void task() {
		log.info("设置redis的wgz键值");
//		redisService.set("wgz", 1);
	}
	
	public boolean isFreighterFromRedis(String keyInfo) {
		String appType = "SINGLE";
		Integer time = 1;
		Integer deadline = 10;
		return redisService.tpsHandlerByAppType(keyInfo, time, deadline, appType);
	}
	public void redisOpe() {
		for (int i=0;i<30;i++) {
			isFreighterFromRedis("ww");
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
