package com.ssafy.a407.dao;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.a407.dto.UserDto;


@Mapper
public interface LoginDao {
	public UserDto login(Map<String, String> map) throws Exception;
	public int insertUser(UserDto user) throws Exception;
	public int removeUser(Map mem) throws Exception;
	public int updateUser(Map mem) throws Exception;
	public int pwupdateUser(Map mem) throws Exception;
	public int pwupdateUser2(Map mem) throws Exception;
	public UserDto profileUser(String email) throws Exception;
	public int updateAdmin(Map user) throws Exception;
	public int checkEmail(String email) throws Exception;
	public int checkNickname(String nickname) throws Exception;
	
	public List<String> profileUserName(String nickname) throws Exception;
	
}
