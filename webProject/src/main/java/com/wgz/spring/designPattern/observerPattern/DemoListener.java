package com.wgz.spring.designPattern.observerPattern;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @Description:定义一个事件监听者,实现ApplicationListener接口，重写 onApplicationEvent() 方法
 * @author: wenguozhang 
 * @date:   2020年7月27日 上午10:11:34  
 */
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
	// 使用onApplicationEvent接收消息
	@Override
	public void onApplicationEvent(DemoEvent event) {
		String msg = event.getMessage();
		System.out.println("接收到的信息是：" + msg);
	}
}