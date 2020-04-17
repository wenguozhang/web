package com.wgz.email;

import org.springframework.mail.MailException;

public class EmailTest {
	//	emailName 发件人邮箱
	//	password 发件人邮箱授权码
	//	email 收件人邮箱
	//	title 邮箱标题
	//	content 邮箱内容
	//	smtp 发件人邮箱Smtp地址
	private static String emailName = "w15710047100@163.com";
	private static String password = "1qaz2wsx";
//	private static String email = "langguojie@126.com";
	private static String email = "1138254567@qq.com";
	private static String title = "下班通知";
	private static String content = "按时下班";
	private static String smtp = "smtp.163.com";
	
	public static void main(String[] args) {
//		发件人邮箱, 发件人邮箱的密码, 收件人邮箱, 邮箱标题, 邮箱内容, 发件人邮箱Smtp地址
        Boolean flag = EmailUtil.init().sendEmail(emailName, password, email, title, content, smtp);
        if(flag)
        	System.out.println("邮件发送成功");
        else
        	System.out.println("邮件发送失败");
	}
}
