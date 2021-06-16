package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.GroupReplyDto;


public interface GroupReplyService {
	public int registReply(GroupReplyDto reply) throws Exception;
	public int modifyReply(GroupReplyDto reply) throws Exception;
	public int removeReply(int rId) throws Exception;
	public int getReplyListCnt(int bId) throws Exception;
	public List<GroupReplyDto> getReplyList(Map map) throws Exception;
}
