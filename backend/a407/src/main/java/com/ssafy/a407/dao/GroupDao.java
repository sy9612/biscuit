package com.ssafy.a407.dao;

import java.util.Map;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.GroupDto;
import com.ssafy.a407.dto.MemberDto;

@Mapper
public interface GroupDao {

	public int removeGroup(int gId) throws Exception;
	public int updateGroup(Map mem) throws Exception;
	public List<GroupDto> selectList() throws Exception;
	public List<GroupDto> selectGroupName(String word) throws Exception;
	public List<GroupDto> selectGroupDesc(String word) throws Exception;
	public List<GroupDto> selectCategory(String word) throws Exception;
	public List<GroupDto> selectRegion(String word) throws Exception;
	public List<GroupDto> selectGId(String word) throws Exception;
	
	 public int insertGroup(GroupDto group) throws Exception;
}
