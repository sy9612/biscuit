package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.BoardDto;

public interface BoardService {

	public int createBoard(BoardDto boardDto) throws Exception;

	public List<BoardDto> searchAll(int currentPage, int noticeFlag) throws Exception;
	public List<BoardDto> searchBId(String word) throws Exception;
	public List<BoardDto> searchTitle(String word, int currentPage, int noticeFlag) throws Exception;
	public List<BoardDto> searchName(String word, int currentPage, int noticeFlag) throws Exception;
	public List<BoardDto> searchContents(String word, int currentPage, int noticeFlag) throws Exception;
	public int update(Map mem) throws Exception;
	public int delete(int bId) throws Exception;
	public int countBoard(String email) throws Exception;
	
}