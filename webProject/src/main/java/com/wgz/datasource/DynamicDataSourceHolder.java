package com.wgz.datasource;

public class DynamicDataSourceHolder {
	
    private static final ThreadLocal<String> HOLDER = new ThreadLocal<String>();

    public static void putDataSourceName(String dataName) {
    	HOLDER.set(dataName);
    }

    public static String getDataSourceName() {
        return HOLDER.get();
    }

    public static void clearDataSourceName() {
    	HOLDER.remove();
    }
    
    public static class DataSourceName {
        public final static String dataSource = "core";
    }

	
}
