package com.wgz.spring.designPattern.singletonPattern.ehan;

/**
 * @Description:饿汉式
 * 没有加锁，执行效率会提高。类加载时就初始化，浪费内存。
 * @author: wenguozhang 
 * @date:   2020年7月28日 上午11:02:05  
 */
public class Singleton {
	private static Singleton instance = new Singleton();

	private Singleton() {
	}

	public static Singleton getInstance() {
		return instance;
	}
}