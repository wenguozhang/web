package com.wgz.task;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:任务扫描
 * @author: wenguozhang 
 * @date:   2019年5月16日 下午3:38:22  
 */
@Slf4j
@Component
public class TaskScan {
	
	@Scheduled(cron="0 */1 * * * *")
	public void task() {
		log.info("任务开始");
		log.info("过了一分钟");
		log.info("任务结束");
	}
}
