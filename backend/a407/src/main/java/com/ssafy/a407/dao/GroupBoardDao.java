package com.ssafy.a407.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.GroupBoardDto;

@Mapper
public interface GroupBoardDao {
	public int createBoard(GroupBoardDto boardDto) throws Exception;

	public List<GroupBoardDto> searchAll(int currentPage, int category, int gId) throws Exception;
	public List<GroupBoardDto> searchBId(String word, int gId) throws Exception;
	public List<GroupBoardDto> searchTitle(String word, int currentPage, int category, int gId) throws Exception;
	public List<GroupBoardDto> searchName(String word, int currentPage, int category, int gId) throws Exception;
	public List<GroupBoardDto> searchContents(String word, int currentPage, int category, int gId) throws Exception;
	
	public int updateBoard(Map mem) throws Exception;

	public int deleteBoard(int bId) throws Exception;

}
