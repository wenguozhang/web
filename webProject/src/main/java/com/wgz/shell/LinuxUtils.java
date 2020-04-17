package com.wgz.shell;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import java.util.Scanner;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

/**
 * @Description:连接linux工具类
 * @author: wenguozhang 
 * @date:   2020年4月10日 下午4:25:18  
 */
public class LinuxUtils {
	
	private static Logger logger =  LoggerFactory.getLogger(LinuxUtils.class);
	
	private String host;
	private String username;
	private String password;
	private int port;
	private Session session;
	private ChannelExec channelExec;
	private Channel channel;
	
	public LinuxUtils(String host, int port, String username, String password) {
		this.host = host;
		this.port = port;
		this.username = username;
		this.password = password;
	}
	
	/**
	 * 远程 登陆Linux
	 */
	public boolean open() {
		if(session != null && session.isConnected()) {
			return true;
		}
		try {
			JSch jsch=new JSch();
			session = jsch.getSession(username, host, port);
			java.util.Properties config = new java.util.Properties();
			config.put("StrictHostKeyChecking", "no");
			session.setConfig(config);
			session.setPassword(password);
			session.connect();
			logger.info("open linuxSession success:" + this.host + ";port:" + this.port + ";name:" + this.username + ";pwd:" + this.password);
			return true;
		} catch (JSchException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 执行Linux command
	 * 并返回结果
	 */
	public void exec(String command){
		if(session == null || !session.isConnected()) {
			open();
		}
		String result="";
		try {
			channelExec = (ChannelExec) session.openChannel("exec");
			channelExec.setCommand(command);
			channelExec.connect();
	        InputStream in = channelExec.getInputStream();  
	        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  
	        String buf = null;
	        while ((buf = reader.readLine()) != null) {
	        	result+= new String(buf.getBytes("gbk"),"UTF-8")+"    <br>\r\n";  
	        }  
		} catch (JSchException e) {
			result+=e.getMessage();
		} catch (IOException e) {
			result+=e.getMessage();
		}finally{
			if(channelExec!=null&&!channelExec.isClosed()){
				channelExec.disconnect();
			}
			if(session!=null&&session.isConnected()){
				session.disconnect();
			}
		}
		logger.info(result);
	}
	
	public void close() {
		if(session!=null&&session.isConnected()){
			session.disconnect();
		}
	}
	
	/**
	 * 执行生成报文shell
	 */
	public boolean execShell(String shellStr){
		boolean result = true ;
		if(session == null || !session.isConnected()) {
			open();
		}
		
		InputStream instream = null ;
		OutputStream outstream = null ;
		try {
			channel = session.openChannel("shell");
			channel.connect();

			instream = channel.getInputStream();
			outstream = channel.getOutputStream();
			
//			String shellStr = "sh /www/backup/0409/test.sh \n";
			logger.info("===>开始调用远程shell命令：" + shellStr);
			outstream.write(shellStr.getBytes());
			outstream.flush();

			StringBuffer res = new StringBuffer();
			BufferedReader reader = new BufferedReader(new InputStreamReader(instream));
			String buf = null;
			System.out.println(reader.readLine()+"33333");
			while ((buf = reader.readLine()) != null) {
				if (buf.length() == 0){
					break;
				}else if(buf.indexOf("!#!")>=0){ //结束标志
					break;
				}
				res.append(buf + "\n\r");
			}
			System.out.println(res);
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			try {
				outstream.close();
				instream.close();
			} catch (Exception e) {}

			if(channel!=null&&!channel.isClosed()){
				channel.disconnect();
			}
			if(session!=null&&session.isConnected()){
				session.disconnect();
			}
		}
		return result;
	}
	
	public static void main(String[] args){
		LinuxUtils linux = new LinuxUtils("39.98.129.71", 22, "root", "Wenzhe@123");
		linux.execShell("sh /www/backup/0409/test.sh \n");
	}
}
