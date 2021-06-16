package com.ssafy.a407Chat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407Chat.dto.ChatRoomDto;

@Mapper
public interface ChatRoomDao {

	public int insertRoom(ChatRoomDto dto) throws Exception;
	public List<ChatRoomDto> selectAllRoom() throws Exception;
	public ChatRoomDto selectById(String roomId) throws Exception;
}
