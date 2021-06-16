package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.GroupDto;
import com.ssafy.a407.dto.GroupMemberDto;
import com.ssafy.a407.dto.MemberDto;
import com.ssafy.a407.dto.UserMemberDto;

public interface MemberService {

	public List<UserMemberDto> getMemberList(int gId) throws Exception; 
	public List<UserMemberDto> getApplyList(int gId) throws Exception; 
	public List<GroupMemberDto> getGroupList(String email) throws Exception;
	
	public int joinMember(MemberDto member) throws Exception;
	
	public int updateMemberPermission(Map map) throws Exception;

	public int cancelApply(Map map) throws Exception;
	public Map applyState(Map map) throws Exception;
	
	public Map getApplyCount(int gId) throws Exception;
	public Map getMemberCount(int gId) throws Exception;
	
	public List<Map> getGroupId(String email) throws Exception;
	public List<Map> getFriends(String email) throws Exception;
}
