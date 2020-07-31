package com.wgz.spring;

import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.wgz.pojo.TaskInfo;
import com.wgz.spring.designPattern.observerPattern.DemoListener;
import com.wgz.spring.designPattern.observerPattern.DemoPublisher;
import com.wgz.utils.SpringContextUtil;

public class SpringApplicationContextTest {
	public static TaskInfo taskInfo1;
	public static TaskInfo taskInfo2;
    public static void main(String[] args) throws InterruptedException {

        // 创建 BeanFactory 容器
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        // 注册 Configuration Class（配置类） -> Spring Bean
        applicationContext.register(SpringApplicationContextTest.class);

        XmlBeanDefinitionReader beanDefinitionReader = new XmlBeanDefinitionReader(applicationContext);

        String xmlResourcePath = "classpath:/dependency-context.xml";
        // 加载 XML 资源，解析并且生成 BeanDefinition
        beanDefinitionReader.loadBeanDefinitions(xmlResourcePath);

        // 启动 Spring 应用上下文
        applicationContext.refresh();
        
        DemoPublisher dp = (DemoPublisher) applicationContext.getBean("demoPublisher");
        dp.publish("你好");

        // 显示地关闭 Spring 应用上下文
        applicationContext.close();
    }
    
}

