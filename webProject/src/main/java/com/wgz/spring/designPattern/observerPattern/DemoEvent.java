package com.wgz.spring.designPattern.observerPattern;

import org.springframework.context.ApplicationEvent;

/**
 * @Description:定义一个事件,继承自ApplicationEvent并且写相应的构造函数
 * @author: wenguozhang 
 * @date:   2020年7月27日 上午10:06:49  
 */
public class DemoEvent extends ApplicationEvent {
	private static final long serialVersionUID = 1L;
	private String message;

	public DemoEvent(Object source, String message) {
		super(source);
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
}