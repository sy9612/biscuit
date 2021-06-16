package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import com.ssafy.a407.dto.UserDto;

public interface LoginService {
	public UserDto login(Map<String, String> map) throws Exception;
	public int join(UserDto user) throws Exception;
	public int remove(Map mem) throws Exception;
	public int update(Map mem) throws Exception;
	public int pwupdate(Map mem) throws Exception;
	public int pwupdate2(Map mem) throws Exception;
	public int updateAdmin(Map mem) throws Exception;
	public int checkEmail(String email) throws Exception;
	public int checkNickname(String nickname) throws Exception;
	public UserDto profile(String email) throws Exception;
	// nickname으로 User 호출하기
	public List<String> profileName(String nickname) throws Exception;
}
