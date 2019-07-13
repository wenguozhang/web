package com.wgz.base.convert;

import java.util.HashMap;
import java.util.Map;

import com.wgz.base.TaskRunException;


public abstract class Converter {
	
	private static final Map<String, Converter> map = new HashMap<String, Converter>();

	public static String getConverter(String type, String paramsStr) throws TaskRunException {
		if(map.size() == 0) {
			map.put("sexConverter", new SexConverter());
			map.put("ClassPeriodCodeConverter", new ClassPeriodCodeConverter());
			map.put("SexConverter", new SexConverter());
			map.put("DateStrConverter", new DateStrConverter());
		}
		Converter converter = (Converter) map.get(type);
		if (converter == null) {
			throw new RuntimeException("没有指定的转换器: " + type);
		}
		return converter.convert(paramsStr.split(","));
	}

	protected abstract String convert(String... str) throws TaskRunException;
}
