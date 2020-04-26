package com.wgz.rabbitmq.spring;
import javax.annotation.Resource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
 
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:applicationContext-rabbitmq.xml" })
public class ListenerTest {
	@Resource
	private RabbitTemplate rabbitTemplate;
	@Test
	public void testSendAsynListener() {
		String sendMsg = "this is direct message";
		Message message = MessageBuilder.withBody(sendMsg.getBytes())
					.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
					.build();
		String routingKey = "direct.test.queue";
		for(int i=0;i<10;i++) {
			rabbitTemplate.send(routingKey, message);
		}
		System.out.println("send ok");
	}
}
