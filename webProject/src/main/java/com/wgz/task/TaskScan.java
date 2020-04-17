package com.wgz.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.wgz.cache.RedisService;
import com.wgz.cache.RedisTest;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:任务扫描
 * @author: wenguozhang 
 * @date:   2019年5月16日 下午3:38:22  
 */
@Slf4j
@Component
public class TaskScan {
	@Autowired
	private RedisTest redisTest;
	@Autowired
    @Qualifier(value = "redisService")
    private RedisService redisService;
	
	@Scheduled(cron="* * * 1 * *")
	public void task() {
		log.info("任务开始");
//		redisService.incr("wgz");
//		redisTest.redisOpe();
		log.info("任务结束");
	}
}
