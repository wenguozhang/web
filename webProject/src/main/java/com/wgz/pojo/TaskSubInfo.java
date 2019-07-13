package com.wgz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;


/**
 * @Description:子任务表task_sub_info
 * @author: wenguozhang 
 * @date:   2019年5月20日 上午9:59:11  
 */
@Data
public class TaskSubInfo {
	
	@TableId(value = "ID", type = IdType.INPUT)
    private Integer id;
    
    private Integer taskId;
 
    private Integer taskSubId;
 
    private String taskSubName;
    
    private Integer enableFlag;
    
    private String createdTime;
    
    private String updatedTime;
    
    private Integer exeSeq;
    
    private Integer maxExeTimes;
    
    private String implClass;
    
    private String sourceType;
    
    private String exeSql;
    
    private String exeUrl;
    
    private String lastTime;
    
    private Integer fullFlag;
    
    private String incSql;
    
    private String extend1;
    
    private String extend2;
}