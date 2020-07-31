package com.wgz.spring.designPattern.observerPattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * @Description:发布事件，可以通过ApplicationEventPublisher 的 publishEvent() 方法发布消息。
 * @author: wenguozhang 
 * @date:   2020年7月27日 上午10:12:24  
 */
@Component
public class DemoPublisher {
	@Autowired
	ApplicationContext applicationContext;

	public void publish(String message) {
		// 发布事件
		applicationContext.publishEvent(new DemoEvent(this, message));
	}
}