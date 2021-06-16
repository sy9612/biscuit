package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.GroupBoardDao;
import com.ssafy.a407.dto.GroupBoardDto;

@Service
public class GroupBoardServiceImpl implements GroupBoardService {

	@Autowired
	private GroupBoardDao dao;
	
	@Override
	public int createBoard(GroupBoardDto board) throws Exception {
		return dao.createBoard(board);
	}

	@Override
	public List<GroupBoardDto> searchAll(int currentPage, int category, int gId) throws Exception {
		return dao.searchAll(currentPage, category, gId);
	}

	@Override
	public List<GroupBoardDto> searchBId(String word, int gId) throws Exception {
		return dao.searchBId(word, gId);
	}

	@Override
	public List<GroupBoardDto> searchTitle(String word, int currentPage, int category, int gId) throws Exception {
		return dao.searchTitle(word, currentPage, category, gId);
	}

	@Override
	public List<GroupBoardDto> searchName(String word, int currentPage, int category, int gId) throws Exception {
		return dao.searchName(word, currentPage, category, gId);
	}

	@Override
	public List<GroupBoardDto> searchContents(String word, int currentPage, int category, int gId) throws Exception {
		return dao.searchContents(word, currentPage, category, gId);
	}

	@Override
	public int update(Map mem) throws Exception {
		return dao.updateBoard(mem);
	}

	@Override
	public int delete(int bId) throws Exception {
		return dao.deleteBoard(bId);
	}

}
