package com.wgz.spring.designPattern.singletonPattern.lanhan;

/**
 * @Description:懒汉式
 * @author: wenguozhang 
 * @date:   2020年7月28日 上午10:58:17  
 */
public class Singleton {
	private static Singleton instance;

	private Singleton() {  //构造方法私有化
	}

	public static synchronized Singleton getInstance() {   //加同步
		if (instance == null) {
			instance = new Singleton();
		}
		return instance;
	}
}
