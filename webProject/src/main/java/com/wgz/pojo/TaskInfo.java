package com.wgz.pojo;

import lombok.Data;


/**
 * @Description:主任务表task_info
 * @author: wenguozhang 
 * @date:   2019年5月20日 上午9:59:11  
 */
@Data
public class TaskInfo {
	
    private Integer id;
 
    private Integer taskId;
 
    private String taskName;
    
    private Integer merid;
    
    private String startTime;
    
    private String taskCron;
    
    private String taskStatus;
    
    private Integer enableFlag;
    
    private String createdTime;
    
    private String updatedTime;
    
    private Integer outTimeFlag;
    
    private String extend1;
    
    private String extend2;
}