package com.ssafy.a407Chat.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407Chat.dto.ChatRoomDto;
import com.ssafy.a407Chat.dto.ChatRoomMemberDto;

@Mapper
public interface ChatRoomMemberDao {
	
	public int insertChatMember(ChatRoomMemberDto dto) throws Exception;
	public List<ChatRoomDto> selectMyRoom(String email) throws Exception;
	public List<ChatRoomMemberDto> selectRoomMember(String roomId) throws Exception;
	public int updateIsJoin(Map map) throws Exception;
}
