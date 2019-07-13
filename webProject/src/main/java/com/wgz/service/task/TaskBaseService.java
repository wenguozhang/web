package com.wgz.service.task;

import java.io.IOException;

import com.wgz.pojo.TaskSubInfo;


/**
 * @Description:业务执行接口
 * @author: wenguozhang 
 * @date:   2019年5月21日 下午6:52:39  
 */
public interface TaskBaseService {
	/**    
	 * @throws IOException 
	 * @throws Exception 
	 * @Description: 执行入口
	 */ 
	public void execute(TaskSubInfo taskSubInfo, Long batchNo) throws Exception;
}
