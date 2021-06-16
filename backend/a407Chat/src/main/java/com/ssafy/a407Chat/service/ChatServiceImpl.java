package com.ssafy.a407Chat.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.a407Chat.dao.ChatMessageDao;
import com.ssafy.a407Chat.dao.ChatRoomDao;
import com.ssafy.a407Chat.dao.ChatRoomMemberDao;
import com.ssafy.a407Chat.dto.ChatMessageDto;
import com.ssafy.a407Chat.dto.ChatRoomDto;
import com.ssafy.a407Chat.dto.ChatRoomMemberDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatServiceImpl implements ChatService{

	private final ObjectMapper ObjectMapper;
	
	@Autowired
	private ChatRoomDao roomDao;
	
	@Autowired
	private ChatMessageDao messageDao;
	
	@Autowired
	private ChatRoomMemberDao roomMemberDao;
	
	@Override
	public List<ChatRoomDto> findAllRoom() throws Exception {
	
		return roomDao.selectAllRoom();
	
	}

	@Override
	public ChatRoomDto findRoomById(String roomId) throws Exception{
		return roomDao.selectById(roomId);
	}

	//랜덤 id로 방생성
	@Override
	public ChatRoomDto createRoom(ChatRoomDto dto) throws Exception{
		String roomName = dto.getRoomName();
		ChatRoomDto chatRoom = ChatRoomDto.create(roomName);
		try {
			roomDao.insertRoom(chatRoom);
			return chatRoom;
			
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}		
		
	}

	@Override
	public <T> void sendMessage(WebSocketSession session, T message) throws Exception{
		try {
			session.sendMessage(new TextMessage(ObjectMapper.writeValueAsString(message)));
			System.out.println("send message : " + message);
		} catch (IOException e) {
			log.error(e.getMessage(), e);

		}
	}

	@Override
	public List<ChatMessageDto> loadMessages(String roomId) throws Exception {
		return messageDao.listMessage(roomId);
	}

	@Override
	public int InviteMember(ChatRoomMemberDto dto) throws Exception {
		// TODO Auto-generated method stub
		return roomMemberDao.insertChatMember(dto);
	}

	@Override
	public List<ChatRoomDto> findMyRoom(String email) throws Exception {
		// TODO Auto-generated method stub
		return roomMemberDao.selectMyRoom(email);
	}

	@Override
	public List<ChatRoomMemberDto> findAllRoomMember(String roomId) throws Exception {
		// TODO Auto-generated method stub
		return roomMemberDao.selectRoomMember(roomId);
	}

	@Override
	public int joinRoom(Map map) throws Exception {
		// TODO Auto-generated method stub
		return roomMemberDao.updateIsJoin(map);
	}
	
	

}
