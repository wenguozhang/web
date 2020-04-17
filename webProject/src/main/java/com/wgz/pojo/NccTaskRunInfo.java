package com.wgz.pojo;

import lombok.Data;

@Data
public class NccTaskRunInfo {
    private Long id;
    
    private Integer taskSubId;
    
    private String merId;
    
    private Long batchNo;
    
    private String fileName;
    
    private String status;
    
    private String errData;
 
    private String errInfo;
    
    private String runTime;
    
}