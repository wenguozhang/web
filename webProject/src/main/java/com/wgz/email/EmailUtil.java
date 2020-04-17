package com.wgz.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import com.sun.mail.util.MailSSLSocketFactory;

// 服务层实现类
public class EmailUtil {
    private static EmailUtil emailUtil = null;
    public static EmailUtil init(){
        if(emailUtil == null){
            emailUtil = new EmailUtil();
        }
        return emailUtil;
    }
    /**    
     * @Description: 邮件发送
     * @param emailName 发件人邮箱
     * @param password 发件人邮箱的密码
     * @param email 收件人邮箱
     * @param title 邮箱标题
     * @param content 邮箱内容
     * @param smtp 发件人邮箱Smtp地址
     */ 
    public  boolean sendEmail(String emailName,String password,String email,String title,String content,String smtp) {
        try {
            MailSSLSocketFactory sf = new MailSSLSocketFactory();
            sf.setTrustAllHosts(true);
            // 建立邮件会话
            Properties props = new Properties(); // 用来在一个文件中存储键-值对的，其中键和值是用等号分隔的，
            // 存储发送邮件服务器的信息
            props.put("mail.smtp.host", smtp);
            // 同时通过验证
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            props.put("mail.smtp.ssl.enable", "true");
            props.put("mail.smtp.ssl.socketFactory", sf);
//            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.transport.protocol", "smtp");
            // 根据属性新建一个邮件会话
            Session s = Session.getInstance(props);
            s.setDebug(false); // 有他会打印一些调试信息。

            // 由邮件会话新建一个消息对象
            MimeMessage message = new MimeMessage(s);
            // 设置邮件
            InternetAddress from = new InternetAddress(emailName); // 发件人邮箱
            message.setFrom(from); // 设置发件人的地址
            // 
            // //设置收件人,并设置其接收类型为TO
            InternetAddress to = new InternetAddress(email); //收件人邮箱
            message.setRecipient(Message.RecipientType.TO, to);

            // 设置标题
            message.setSubject(title); 

            // 设置信件内容
            message.setContent(content,"text/html;charset=gbk"); // 发送HTML邮件
                                                
            // 设置发信时间
            message.setSentDate(new Date());

            // 存储邮件信息
            message.saveChanges();
        
            // 发送邮件    
            Transport transport = s.getTransport("smtp");
            // 以smtp方式登录邮箱,第一个参数是发送邮件用的邮件服务器SMTP地址,第二个参数为用户名,第三个参数为密码
            transport.connect(smtp,emailName,password);
            // 发送邮件,其中第二个参数是所有已设好的收件人地址
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}