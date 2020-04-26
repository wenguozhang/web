package com.wgz.rabbitmq.spring;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
public class MyMessageListener implements MessageListener{
	@Override
	public void onMessage(Message message) {
		System.out.println("received: " + message);
	}
}