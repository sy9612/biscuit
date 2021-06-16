package com.ssafy.a407.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.ReplyDto;

@Mapper
public interface ReplyDao {
	public int insertReply(ReplyDto reply) throws Exception;
	public int updateReply(ReplyDto reply) throws Exception;
	public int deleteReply(int rId) throws Exception;
	public int getReplyListCnt(int bId) throws Exception;
	public List<ReplyDto> getReplyList(Map map) throws Exception;
	public int countReply(String email) throws Exception;
	
}
