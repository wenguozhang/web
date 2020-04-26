package com.wgz.datasource.aop;

import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.wgz.datasource.DataSourceCheckUtil;
import com.wgz.datasource.DynamicDataSourceHolder;


@Aspect
@Component
public class ChooseDataSourceAspect {

	@Pointcut("@annotation(com.wgz.datasource.aop.ChooseDataSource)")
    public void addAdvice(){}
	
	@Around("addAdvice()")
	public Object around(ProceedingJoinPoint joinPoint) {
		Object proceed = null;
		try {
			Object[] args = joinPoint.getArgs();
			Method method = getMethod(joinPoint, args);
			// 获取数据库名称参数
			ChooseDataSource chooseDataSource = method.getAnnotation(ChooseDataSource.class);

			if (chooseDataSource != null) {
				String dataSourceName = chooseDataSource.dataSourceName();
				// 检查数据库名称是否存在
				if (DataSourceCheckUtil.check(dataSourceName)) {
					DynamicDataSourceHolder.putDataSourceName(dataSourceName);
				} else {
					DynamicDataSourceHolder.putDataSourceName("core");
				}
				// 执行方法
				proceed = joinPoint.proceed();
				// 执行完成删除
				DynamicDataSourceHolder.clearDataSourceName();
			} else {
				proceed = joinPoint.proceed();
			}
		} catch (Throwable e) {
			DynamicDataSourceHolder.clearDataSourceName();
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return proceed;
	}
    private Method getMethod(ProceedingJoinPoint joinPoint, Object[] args) throws NoSuchMethodException {
        String methodName = joinPoint.getSignature().getName();
        Class<?> clazz = joinPoint.getTarget().getClass();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (methodName.equals(method.getName())) {
                return method;
            }
        }
        return null;
    }
    
}
