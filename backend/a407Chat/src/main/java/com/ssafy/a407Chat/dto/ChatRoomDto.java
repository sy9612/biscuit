package com.ssafy.a407Chat.dto;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatRoomDto {
	private String roomId;
	private String roomName;

	public ChatRoomDto() {
		
	}
	
	public ChatRoomDto(String roomId, String roomName) {
		this.roomId = roomId;
		this.roomName = roomName;
	}
	
	public static ChatRoomDto create(String roomName) {
		ChatRoomDto chatRoom = new ChatRoomDto();
		chatRoom.roomId = UUID.randomUUID().toString();
		chatRoom.roomName = roomName;
		return chatRoom;
		
	}
	
}
	
	
