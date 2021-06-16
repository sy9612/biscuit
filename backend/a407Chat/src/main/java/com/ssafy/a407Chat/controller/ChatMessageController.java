package com.ssafy.a407Chat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;

import com.ssafy.a407Chat.dao.ChatMessageDao;
import com.ssafy.a407Chat.dto.ChatMessageDto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ChatMessageController {
	@Autowired
	private ChatMessageDao dao;
	private final SimpMessageSendingOperations messagingTemplate;
	
	@MessageMapping("/chat/message")
	public void message(ChatMessageDto message) throws Exception{
		
		if((message.getType()).equals("JOIN")) {
			message.setMessage(message.getNickname() + "님이 입장하였습니다.");
		}
		else if((message.getType()).equals("ENTER")) {
			message.setMessage(message.getNickname() + "in socket");
		}
		
		System.out.println("message : " + message.getMessage());
		dao.insertMessage(message);
		messagingTemplate.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
	
	
}
