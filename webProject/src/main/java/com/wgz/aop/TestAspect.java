package com.wgz.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;


@Aspect
public class TestAspect {
	
	@Pointcut("execution(* com.wgz.aop.HelloWorld.*())")
    public void record(){}
	
	@Before("record()")
    public void before(){
        System.out.println("before................");
    }

    @After("record()")
    public void after(){
        System.out.println("after.................");
    }
    
	@Around("record()")
	public void around(ProceedingJoinPoint joinPoint) {
		try {
			System.out.println("around before............");
	        joinPoint.proceed(); //执行完成目标方法
	        System.out.println("around after..............");
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
    
}
