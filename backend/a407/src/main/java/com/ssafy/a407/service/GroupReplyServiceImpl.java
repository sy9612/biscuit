package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.GroupReplyDao;
import com.ssafy.a407.dto.GroupReplyDto;

@Service
public class GroupReplyServiceImpl implements GroupReplyService {

	@Autowired
	private GroupReplyDao dao;
	
	@Override
	public int registReply(GroupReplyDto reply) throws Exception {
		return dao.insertReply(reply);
	}

	@Override
	public int modifyReply(GroupReplyDto reply) throws Exception {
		return dao.updateReply(reply);
	}

	@Override
	public int removeReply(int rId) throws Exception {
		return dao.deleteReply(rId);
	}

	@Override
	public int getReplyListCnt(int bId) throws Exception {
		return dao.getReplyListCnt(bId);
	}

	@Override
	public List<GroupReplyDto> getReplyList(Map map) throws Exception {
		return dao.getReplyList(map);
	}

}
