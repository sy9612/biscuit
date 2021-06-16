package com.ssafy.a407.service;

import javax.mail.internet.MimeMessage;

public interface EmailService {
	//public static final String ePw;
	public MimeMessage createMessage(String to) throws Exception;
	public void sendSimpleMessage(String to) throws Exception ;
	
}
