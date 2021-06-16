package com.ssafy.a407.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.GroupReplyDto;

@Mapper
public interface GroupReplyDao {
	public int insertReply(GroupReplyDto reply) throws Exception;
	public int updateReply(GroupReplyDto reply) throws Exception;
	public int deleteReply(int rId) throws Exception;
	public int getReplyListCnt(int bId) throws Exception;
	public List<GroupReplyDto> getReplyList(Map map) throws Exception;
}
