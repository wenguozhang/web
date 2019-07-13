package com.wgz.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wgz.dao.TaskDao;
import com.wgz.pojo.TaskInfo;

@Service("taskService")
public class TaskService {
	@Autowired
	private TaskDao tDao;
	
	
	public void run() {
		
		List<TaskInfo> list = tDao.getTask();
		System.out.println(list);
		
	}
}
