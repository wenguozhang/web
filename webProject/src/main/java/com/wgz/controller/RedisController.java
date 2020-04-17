package com.wgz.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wgz.cache.RedisService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/redis")
public class RedisController {
	@Autowired
	@Qualifier(value = "redisService")
	private RedisService redisService;
	
	@RequestMapping("/hello")
	public String hello() {
		log.info("redis test...");
		redisService.set("aa", "七夕快乐!");
//		String aa = redisService.get("aa", String.class);
//		log.info(aa);
		return "redis opetate success!";
	}
}
