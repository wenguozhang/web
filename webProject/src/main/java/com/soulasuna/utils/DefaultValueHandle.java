package com.soulasuna.utils;

public class DefaultValueHandle implements ClearHandle<String> {
    /**
     * 设置默认值
     * @param s
     * @return
     */
    @Override
    public String clearHandle(String s) {
        return "".equals(s)?"default":s;
    }
}
