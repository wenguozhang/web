package com.soulasuna.utils;

public class NonNullHandle implements ClearHandle<String> {

    /**
     * null转成空串
     * @param s     转换字符串
     * @return      传唤完成字符串
     */
    @Override
    public String clearHandle(String s) {
        return s == null? "": s;
    }
}
