/**
 * 
 */
package com.test.mail;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.junit.Test;

/**
 * 简单邮件（不带附件的邮件）发送器
 * @author huozhicheng@gmail.com
 * @date 2012-12-3上午11:21:34
 * @version 1.0
 */
public class SimpleMailSender {

	/**  
	 * 以文本格式发送邮件  
	 * @param mailInfo 待发送的邮件的信息  
	 */   
	public boolean sendTextMail(MailSenderInfo mailInfo) {   
		// 判断是否需要身份认证   
		MyAuthenticator authenticator = null;   
		Properties pro = mailInfo.getProperties();
		if (mailInfo.isValidate()) {
			// 如果需要身份认证，则创建一个密码验证器   
			authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());   
		}  
		// 根据邮件会话属性和密码验证器构造一个发送邮件的session   
		Session sendMailSession = Session.getDefaultInstance(pro,authenticator);   
		try {   
			// 根据session创建一个邮件消息   
			Message mailMessage = new MimeMessage(sendMailSession);   
			// 创建邮件发送者地址   
			Address from = new InternetAddress(mailInfo.getFromAddress());   
			// 设置邮件消息的发送者   
			mailMessage.setFrom(from);   
			// 创建邮件的接收者地址，并设置到邮件消息中   
			Address to = new InternetAddress(mailInfo.getToAddress());   
			mailMessage.setRecipient(Message.RecipientType.TO,to);   
			// 设置邮件消息的主题   
			mailMessage.setSubject(mailInfo.getSubject());   
			// 设置邮件消息发送的时间   
			mailMessage.setSentDate(new Date());   
			// 设置邮件消息的主要内容   
			String mailContent = mailInfo.getContent();   
			mailMessage.setText(mailContent);   
			// 发送邮件   
			Transport.send(mailMessage);  
			return true;   
		} catch (MessagingException ex) {   
			ex.printStackTrace();   
		}   
		return false;   
	}
	@Test
	public void testSend(){  
		//这个类主要是设置邮件  
		MailSenderInfo mailInfo = new MailSenderInfo();   
		mailInfo.setMailServerHost("smtp.163.com");   
		mailInfo.setMailServerPort("25");   
		mailInfo.setValidate(true);   
		mailInfo.setUserName("taotaotong_test@163.com");   
		mailInfo.setPassword("abc00000de");//您的邮箱密码   
		mailInfo.setFromAddress("taotaotong_test@163.com");   
		mailInfo.setToAddress("22817237@qq.com");   
		mailInfo.setSubject("亲爱的Horrison");   
		mailInfo.setContent("你好，这个是找回密码的邮件，请查收！");   
		//这个类主要来发送邮件  
		SimpleMailSender sms = new SimpleMailSender();  
		sms.sendTextMail(mailInfo);//发送文体格式   
	}
}
