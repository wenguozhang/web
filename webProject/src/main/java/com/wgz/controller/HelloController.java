package com.wgz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;
@Slf4j
@Controller
@RequestMapping("/hello")
public class HelloController {
	
	@RequestMapping("/sayHello")
	public String say() {
		log.info("helloWord");
		return "helloWord";
	}
	
}