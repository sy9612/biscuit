package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.GroupDao;
import com.ssafy.a407.dao.MemberDao;
import com.ssafy.a407.dto.GroupDto;
import com.ssafy.a407.dto.GroupMemberDto;
import com.ssafy.a407.dto.MemberDto;
import com.ssafy.a407.dto.UserMemberDto;

@Service
public class MemberServiceImpl implements MemberService{
	@Autowired
	MemberDao dao;
	
	@Override
	public List<UserMemberDto> getMemberList(int gId) throws Exception {
		return dao.selectMemberList(gId);
	}

	@Override
	public List<UserMemberDto> getApplyList(int gId) throws Exception {
		return dao.selectApplyList(gId);
	}

	@Override
	public List<GroupMemberDto> getGroupList(String email) throws Exception {
//		System.out.println("service. getGroupList. email: " + email);
		return dao.selectGroupList(email);
	}
	

	@Override
	public int joinMember(MemberDto member) throws Exception {
		return dao.insertMember(member);
	}

	@Override
	public int updateMemberPermission(Map input) throws Exception {
		return dao.updateMemberPermission(input);
	}

	@Override
	public int cancelApply(Map map) throws Exception {
		return dao.deleteMemberApply(map);
	}

	@Override
	public Map applyState(Map map) throws Exception {
//		System.out.println("service.applyState.Map :  " + map);
		return dao.applyState(map);
	}
	
	@Override
	public Map getApplyCount(int gId) throws Exception{
//		System.out.println("service.getApplyCount.gId :  " + gId);
		return dao.selectApplyCount(gId);
	}
	
	@Override
	public Map getMemberCount(int gId) throws Exception{
		return dao.selectMemberCount(gId);
	}

	@Override
	public List<Map> getGroupId(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGroupId(email);
	}

	@Override
	public List<Map> getFriends(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectFriends(email);
	}
}
