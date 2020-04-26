package com.wgz.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class DynamicDataSource extends AbstractRoutingDataSource {
	
	@Override
	protected Object determineCurrentLookupKey() {
		String dataSourceName = DynamicDataSourceHolder.getDataSourceName();
        if (dataSourceName == null) {
            dataSourceName = "core";
        }
        System.out.println("当前选择的数据源是:" + dataSourceName);
        return dataSourceName;
	}

	/**
	 * 设置数据源
	 * @param dataSource 数据源名称
	 */
	public static void setDataSource(String dataSource){
		DynamicDataSourceHolder.putDataSourceName(dataSource);
	}
 
 
	/**
	 * 获取数据源
	 * @return
	 */
	public static String getDatasource() {
		return DynamicDataSourceHolder.getDataSourceName();
	}
 
	/**
	 * 清除数据源
	 */
	public static void clearDataSource(){
		DynamicDataSourceHolder.clearDataSourceName();
	}
}
