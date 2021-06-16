package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.GroupBoardDto;


public interface GroupBoardService {
	public int createBoard(GroupBoardDto boardDto) throws Exception;

	public List<GroupBoardDto> searchAll(int currentPage, int category, int gId) throws Exception;
	public List<GroupBoardDto> searchBId(String word, int gId) throws Exception;
	public List<GroupBoardDto> searchTitle(String word, int currentPage, int category, int gId) throws Exception;
	public List<GroupBoardDto> searchName(String word, int currentPage, int category, int gId) throws Exception;
	public List<GroupBoardDto> searchContents(String word, int currentPage, int category, int gId) throws Exception;

	public int update(Map mem) throws Exception;

	public int delete(int bId) throws Exception;
}
