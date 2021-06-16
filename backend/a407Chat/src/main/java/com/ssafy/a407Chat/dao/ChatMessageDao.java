package com.ssafy.a407Chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407Chat.dto.ChatMessageDto;

@Mapper
public interface ChatMessageDao {

	public int insertMessage(ChatMessageDto dto) throws Exception;
	public List<ChatMessageDto> listMessage(String roomId) throws Exception;
}
