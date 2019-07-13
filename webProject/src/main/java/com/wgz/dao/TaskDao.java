package com.wgz.dao;

import java.util.List;

import com.wgz.pojo.TaskInfo;

public interface TaskDao {
	public List<TaskInfo> getTask();
	
	public TaskInfo getTaskById(Integer id);
}
