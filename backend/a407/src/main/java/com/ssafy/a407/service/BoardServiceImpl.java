package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.BoardDao;
import com.ssafy.a407.dto.BoardDto;

@Service
public class BoardServiceImpl implements BoardService{

	@Autowired
	BoardDao dao;
	
	@Override
	public int createBoard(BoardDto board) throws Exception {
		// TODO Auto-generated method stub
		return dao.createBoard(board);
	}

	@Override
	public List<BoardDto> searchAll(int currentPage, int noticeFlag) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchAll(currentPage, noticeFlag);
	}

	@Override
	public List<BoardDto> searchBId(String word) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchBId(word);
	}

	@Override
	public List<BoardDto> searchTitle(String word, int currentPage, int noticeFlag) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchTitle(word, currentPage, noticeFlag);
	}

	@Override
	public List<BoardDto> searchName(String word, int currentPage, int noticeFlag) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchName(word, currentPage, noticeFlag);
	}

	@Override
	public List<BoardDto> searchContents(String word, int currentPage, int noticeFlag) throws Exception {
		// TODO Auto-generated method stub
		return dao.searchContents(word, currentPage, noticeFlag);
	}

	@Override
	public int update(Map mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.updateBoard(mem);
	}

	@Override
	public int delete(int bId) throws Exception {
		// TODO Auto-generated method stub
		return dao.deleteBoard(bId);
	}

	@Override
	public int countBoard(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.countBoard(email);
	}
	
}