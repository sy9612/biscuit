package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.ReplyDto;

public interface ReplyService {
	public int registReply(ReplyDto reply) throws Exception;
	public int modifyReply(ReplyDto reply) throws Exception;
	public int removeReply(int rId) throws Exception;
	public int getReplyListCnt(int bId) throws Exception;
	public List<ReplyDto> getReplyList(Map map) throws Exception;
	public int countReply(String email) throws Exception;
 
	
}
