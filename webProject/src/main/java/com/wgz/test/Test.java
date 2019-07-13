package com.wgz.test;

import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;

public class Test {
	public static void main(String[] args) throws UnknownHostException, SQLException {
		long nanos = TimeUnit.MILLISECONDS.toNanos(-1);
		System.out.println(nanos);
		DruidDataSource source = new DruidDataSource();
//		Connection connection = source.getConnection(3L);
//		DataSource ds = new DataSource();
	}
}
