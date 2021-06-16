package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.GroupDto;
import com.ssafy.a407.dto.MemberDto;

public interface GroupService {

	public int remove(int gId) throws Exception;
	public int update(Map mem) throws Exception;
	public List<GroupDto> searchList() throws Exception;
	public List<GroupDto> searchGroupName(String word) throws Exception;
	public List<GroupDto> searchGroupDesc(String word) throws Exception;
	public List<GroupDto> searchCategory(String word) throws Exception;
	public List<GroupDto> searchRegion(String word) throws Exception;
	
	public int createGroup(GroupDto group) throws Exception;
	public List<GroupDto> searchGId(String word) throws Exception;
}
