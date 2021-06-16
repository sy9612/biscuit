package com.ssafy.a407Chat.dto;

import java.util.List;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatMessageDto {
	
	//메시지 타입 : 입장, 채팅
	public enum MessageType{
		ENTER, TALK, JOIN
	}
	private String type;
	private String roomId; // 방번호
	private String email; // sender 이메일
	private String nickname; //sender 닉네임
	private String message;
	
	public ChatMessageDto() {
		// TODO Auto-generated constructor stub
	}

	
	
	
	
	
	
}
