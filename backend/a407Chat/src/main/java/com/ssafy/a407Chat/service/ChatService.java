package com.ssafy.a407Chat.service;

import java.util.List;
import java.util.Map;

import org.springframework.web.socket.WebSocketSession;

import com.ssafy.a407Chat.dto.ChatMessageDto;
import com.ssafy.a407Chat.dto.ChatRoomDto;
import com.ssafy.a407Chat.dto.ChatRoomMemberDto;

public interface ChatService {
	public List<ChatRoomDto> findAllRoom() throws Exception;
	public ChatRoomDto findRoomById(String roomId) throws Exception;
	public ChatRoomDto createRoom(ChatRoomDto dto) throws Exception;
	public <T> void sendMessage(WebSocketSession session, T message) throws Exception;
	public List<ChatMessageDto> loadMessages(String roomId) throws Exception;
	public int InviteMember(ChatRoomMemberDto dto) throws Exception;
	public List<ChatRoomDto> findMyRoom(String email) throws Exception;
	public List<ChatRoomMemberDto> findAllRoomMember(String roomId) throws Exception;
	public int joinRoom(Map map) throws Exception;
}
