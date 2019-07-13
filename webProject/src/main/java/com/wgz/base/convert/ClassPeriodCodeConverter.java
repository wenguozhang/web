package com.wgz.base.convert;

import com.wgz.base.ResultCode;
import com.wgz.base.TaskRunException;

/**
 * @Description:班级周期编码转换器
 * @author: wenguozhang 
 * @date:   2019年6月26日 下午5:39:23  
 */
public class ClassPeriodCodeConverter extends Converter {
	
	@Override
	public String convert(String... str) throws TaskRunException {
		if(str.length<1)
			throw new TaskRunException(ResultCode.转换器参数异常, "FeeCodeConverter转换器配置异常");
		
		String ret = str[0].trim()+"1101";
		return ret;
	}
}
