package com.wgz.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

import lombok.Data;

@Data
public class TaskSequence {
	
	@TableId(value = "ID", type = IdType.INPUT)
    private Long id;
 
    private String batchDate;
 
    private int batchSeq;
    
    private String createTime;
    
    private String modifyTime;
}