package com.wgz.base.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.Assert;

import com.wgz.base.ResultCode;
import com.wgz.base.TaskRunException;

public class DateStrConverter extends Converter {
	@Override
	public String convert(String... str) throws TaskRunException {
		if(str.length==0)
			throw new TaskRunException(ResultCode.转换器参数异常, "DateStrConverter转换器配置异常");
		if(str[0].length() != 8)
			throw new TaskRunException(ResultCode.非法日期字符串,"日期长度不是8位");
		
		return str[0].substring(0, 4)+"-"+str[0].substring(4, 6)+"-"+str[0].substring(6, 8);
	}
	
	public static String conversionDate (String input, String sourcePattern, String targetPattern) throws TaskRunException {
		Date date;
		try {
	        Assert.hasText(input,"error : conversionDate input can not be empty");
	        Assert.hasText(sourcePattern,"error : conversionDate input can not be empty");
	        Assert.hasText(targetPattern,"error : conversionDate input can not be empty");
	        // 解析源格式
	        SimpleDateFormat sourceFormat = new SimpleDateFormat(sourcePattern);
            date = sourceFormat.parse(input);
        } catch (ParseException e) {
            throw new TaskRunException(ResultCode.非法日期字符串);
        }
        if (date == null || "".equals(date)){
            return null;
        }
        // 格式化目标格式
        SimpleDateFormat targetFormat = new SimpleDateFormat(targetPattern);
        return targetFormat.format(date);
    }
	public static void main(String[] args) throws TaskRunException {
		System.out.println("20180201".substring(0, 4)+"-"+"20180201".substring(4, 6)+"-"+"20180201".substring(6, 8));
	}
}
