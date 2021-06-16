package com.ssafy.a407.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.BoardDto;

@Mapper
public interface BoardDao {

	public int createBoard(BoardDto board) throws Exception;

	public List<BoardDto> searchAll(int currentPage, int noticeFlag) throws Exception;
	public List<BoardDto> searchBId(String word) throws Exception;
	public List<BoardDto> searchTitle(String word, int currentPage, int noticeFlag) throws Exception;
	public List<BoardDto> searchName(String word, int currentPage, int noticeFlag) throws Exception;
	public List<BoardDto> searchContents(String word, int currentPage, int noticeFlag) throws Exception;
	public int updateBoard(Map mem) throws Exception;
	public int deleteBoard(int bId) throws Exception;
	public int countBoard(String email) throws Exception;

}
