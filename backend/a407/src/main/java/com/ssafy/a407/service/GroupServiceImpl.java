package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.GroupDao;
import com.ssafy.a407.dto.GroupDto;
import com.ssafy.a407.dto.MemberDto;

@Service
public class GroupServiceImpl implements GroupService{
	
	@Autowired
	GroupDao dao;
	
	@Override
	public int remove(int gId) throws Exception {
		// TODO Auto-generated method stub
		return dao.removeGroup(gId);
	}

	@Override
	public int update(Map mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.updateGroup(mem);
	}
	
	@Override
	public List<GroupDto> searchList() throws Exception {
		System.out.println("service : searchList()");
		return dao.selectList();
	}

	@Override
	public List<GroupDto> searchGroupName(String word) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGroupName(word);
	}

	@Override
	public List<GroupDto> searchGroupDesc(String word) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGroupDesc(word);
	}

	@Override
	public List<GroupDto> searchCategory(String word) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectCategory(word);
	}

	@Override
	public List<GroupDto> searchRegion(String word) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectRegion(word);
	}
	
	@Override
	public List<GroupDto> searchGId(String word) throws Exception {
		// TODO Auto-generated method stub
		return dao.selectGId(word);
	}

	@Override
	public int createGroup(GroupDto group) throws Exception {
		return dao.insertGroup(group);
	}

	
}
