package com.wgz.quartz;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.SimpleTrigger;
import org.quartz.TriggerBuilder;
import org.quartz.impl.StdSchedulerFactory;

/**
 * @Description:简单调度器例子
 * @author: wenguozhang 
 * @date:   2020年4月28日 下午4:23:12  
 */
public class HelloScheduler {
	public static void main(String[] args) throws SchedulerException{
		Date date = new Date();
		
		// 1、创建调度器Scheduler
		SchedulerFactory sfact = new StdSchedulerFactory();
		Scheduler scheduler = sfact.getScheduler();

		// 2、创建JobDetail实例，并与HelloJob类绑定(Job执行内容)
		JobDetail jobDetail = JobBuilder.newJob(HelloJob.class).withIdentity("myjob", "jobgroup1").build();
		
		// 3、构建Trigger实例,每隔2s执行一次
		Date endDate = new Date();		endDate.setTime(endDate.getTime() + 10000);
		SimpleTrigger trigger = (SimpleTrigger) TriggerBuilder.newTrigger().withIdentity("myTrigger", "trigroup1")
				.startAt(date)	//开始时间
				.endAt(endDate)	//结束时间
				.withSchedule(SimpleScheduleBuilder.simpleSchedule()
						.withIntervalInSeconds(2)	//每隔2s执行一次
						.withRepeatCount(SimpleTrigger.REPEAT_INDEFINITELY)).build();
		//4、执行
		scheduler.scheduleJob(jobDetail, trigger);
		scheduler.start();
	}
}
