package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.ReplyDao;
import com.ssafy.a407.dto.ReplyDto;

@Service
public class ReplyServiceImpl implements ReplyService {

	@Autowired
	private ReplyDao dao;

	@Override
	public int registReply(ReplyDto reply) throws Exception {
		return dao.insertReply(reply);
	}

	@Override
	public int modifyReply(ReplyDto reply) throws Exception {
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
	public List<ReplyDto> getReplyList(Map map) throws Exception {
		return dao.getReplyList(map);
	}

	@Override
	public int countReply(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.countReply(email);
	}
	
	
}
