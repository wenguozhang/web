package com.wgz.base.convert;

public class SexConverter extends Converter {
	@Override
	public String convert(String... str) {
		if("1".equals(str[0].trim()))
			return "1";
		if("2".equals(str[0].trim()))
			return "2";
		return "3";
	}
}
