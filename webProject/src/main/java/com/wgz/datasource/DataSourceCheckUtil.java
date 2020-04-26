package com.wgz.datasource;

import java.util.Arrays;
import java.util.List;

public class DataSourceCheckUtil {
    static List<String> dataSourceList = Arrays.asList("core", "third");
    
    public static boolean check(String dataSourceName) {
        boolean flag = false;
        if (dataSourceList.contains(dataSourceName)) {
            flag = true;
        }
        return flag;
    }
}
