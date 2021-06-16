package com.ssafy.a407.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.a407.dao.LoginDao;
import com.ssafy.a407.dto.UserDto;


@Service
public class LoginServiceImpl implements LoginService {

	@Autowired //DAO 주입
	private LoginDao dao;
	
	@Override
	public UserDto login(Map<String, String> map) throws Exception {
//		System.out.println("service : " + map);
		return dao.login(map);
	}

	@Override
	public int join(UserDto user) throws Exception {
//		System.out.println("service : " + user);
		return dao.insertUser(user);
	}

	@Override
	public int remove(Map mem) throws Exception {
//		System.out.println("service : " + mem);
		return dao.removeUser(mem);
		
	}

	@Override
	public int update(Map mem) throws Exception {
//		System.out.println("service : " + mem);
		return dao.updateUser(mem);
	}

	@Override
	public int pwupdate(Map mem) throws Exception {
		return dao.pwupdateUser(mem);
	}
	@Override
	public int pwupdate2(Map mem) throws Exception {
		// TODO Auto-generated method stub
		return dao.pwupdateUser2(mem);
	}

	@Override
	public UserDto profile(String email) throws Exception {
		return dao.profileUser(email);
	}

	@Override
	public int updateAdmin(Map mem) throws Exception {
		return dao.updateAdmin(mem);
	}

	@Override
	public List<String> profileName(String nickname) throws Exception {
		return dao.profileUserName(nickname);
	}

	@Override
	public int checkEmail(String email) throws Exception {
		// TODO Auto-generated method stub
		return dao.checkEmail(email);
	}

	@Override
	public int checkNickname(String nickname) throws Exception {
		// TODO Auto-generated method stub
		return dao.checkNickname(nickname);
	}

}
