package com.wgz.quartz;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

public class HelloJob implements Job {
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException{
		Date date = new Date();
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println("Current exec time is : "+ sf.format(date));
		System.out.println("Hello world!");
	}
}
