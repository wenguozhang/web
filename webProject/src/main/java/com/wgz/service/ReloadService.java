package com.wgz.service;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

/**
 * @Description:应用启动时执行重载 
 * @author: wenguozhang 
 * @date:   2019年7月3日 下午4:59:53  
 */
@Slf4j
@Service
public class ReloadService implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		log.info("使用org.springframework.beans.factory.InitializingBean执行任务");
	}
	
	@PostConstruct
	public void init(){
		log.info("使用@PostConstruct执行任务");
	}
	
}
