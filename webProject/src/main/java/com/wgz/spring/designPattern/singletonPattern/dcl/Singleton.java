package com.wgz.spring.designPattern.singletonPattern.dcl;

/**
 * @Description:双检锁/双重校验锁（DCL，即 double-checked locking）
 * @author: wenguozhang 
 * @date:   2020年7月28日 上午11:03:26  
 */
public class Singleton {
	private volatile static Singleton singleton;

	private Singleton() {
	}

	public static Singleton getSingleton() {
		if (singleton == null) {
			synchronized (Singleton.class) {
				if (singleton == null) {
					singleton = new Singleton();
				}
			}
		}
		return singleton;
	}
}