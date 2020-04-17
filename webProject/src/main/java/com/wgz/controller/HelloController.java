package com.wgz.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@RestController
@RequestMapping("/hello")
public class HelloController {
	
	@RequestMapping("/sayHello")
	public String say() {
		log.info("helloWord");
		return "helloWord";
	}
	
}