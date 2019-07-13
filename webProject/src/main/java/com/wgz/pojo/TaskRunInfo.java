package com.wgz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;


/**
 * @Description:任务执行记录表 task_run_info
 * @author: wenguozhang 
 * @date:   2019年5月20日 上午9:59:11  
 */
@Data
public class TaskRunInfo {
	
	@TableId(value = "ID", type = IdType.INPUT)
    private Integer id;
    
    private Integer taskId;
 
    private Integer taskSubId;
    
    private Long batchNo;
    
    private String startTime;
    
    private String lastTime;
    
    private String taskStatus;
    
    private Integer exeSeq;
 
    private String resultCode;
    
    private String resultMessage;
    
    private String createdTime;
    
    private String updatedTime;
}