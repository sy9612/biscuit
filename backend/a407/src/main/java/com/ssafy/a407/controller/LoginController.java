package com.ssafy.a407.controller;
import java.io.Console;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a407.dto.UserDto;
import com.ssafy.a407.service.JwtService;
import com.ssafy.a407.service.LoginService;



@CrossOrigin(origins = { "*" }, maxAge = 6000)

@RestController
@RequestMapping("/user")
public class LoginController{


	@Autowired
	private JwtService jwtService;
	
	@Autowired
	private LoginService login;
	

	public static final Logger logger = LoggerFactory.getLogger(LoginController.class);
	
	@GetMapping(value = "/test")
	private String test() {
		return "test";
	}
	
	//로그인
	@PostMapping(value = "/login")
    private ResponseEntity login(@RequestBody Map mem) {
        Map result = new HashMap();
//        System.out.println("email : " + mem.get("email") + " pw : " + mem.get("password"));
        ResponseEntity entity = null;
        try {
        	String pw = getHashPassword((String)mem.get("password"));
        	mem.replace("password", pw);
        	
            UserDto member = login.login(mem);
            if (member == null) {
//            	System.out.println("if");
            	result.put("success", "fail"); 
                entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
            }
            else {
                String token = jwtService.create(member);
//                System.out.println(token);
                logger.trace("token " , token);
                result.put("user", member);
                result.put("x-access-token", token);
                result.put("success", "success");                
                

            entity = new ResponseEntity<>(result, HttpStatus.OK);
            
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        //model.addAttribute("msg", "로그인 중 문제가 발생했습니다.");
	        result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
	    }
	    return entity;
	}

	
	//회원가입
	@PostMapping(value = "/join")
    private ResponseEntity register(@RequestBody UserDto user) {
//		System.out.println("email : "+user.getEmail()+"/ nickname : "+user.getNickname()+" / password : "+user.getPassword());
		Map result = new HashMap();
        ResponseEntity entity = null;
        user.setPicture("https://user-images.githubusercontent.com/50867723/108148062-68b5e300-7113-11eb-82c9-d20ce9eeaeb4.png");
        try {
        	user.setPassword(getHashPassword(user.getPassword())); //비밀번호 암호화
//        	System.out.println(user.getPassword());
        	if (login.join(user) == 1) {
        		result.put("success", "success");
        		entity = new ResponseEntity<>(result, HttpStatus.OK);
        		
        	}
        	else {
        		result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
        	}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//회원탈퇴
	@PostMapping(value = "/delete")
	private ResponseEntity delete(@RequestBody Map mem) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			mem.put("password",(getHashPassword((String)mem.get("password"))));
			if (login.remove(mem) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
				
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	//회원정보수정
	@PutMapping(value = "/update")
	private ResponseEntity update(@RequestBody Map user) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			if (login.update(user) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	//비밀번호변경
	@PutMapping(value = "/pwupdate")
	private ResponseEntity pwupdate(@RequestBody Map mem) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			String pw = getHashPassword((String) mem.get("password"));
        	mem.replace("password", pw);
			String newpw = getHashPassword((String) mem.get("newpassword"));
			mem.replace("newpassword", newpw);
			if (login.pwupdate(mem) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);			
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	//비밀번호변경
		@PutMapping(value = "/pwupdate2")
		private ResponseEntity pwupdate2(@RequestBody Map mem) {
			ResponseEntity entity = null;
			System.out.println("update ========");
			Map result = new HashMap();
			try {
				String pw = getHashPassword((String) mem.get("password"));
	        	mem.replace("password", pw);
				String newpw = getHashPassword((String) mem.get("newpassword"));
				mem.replace("newpassword", newpw);
				if (login.pwupdate2(mem) == 1) {
					result.put("success", "success");
					entity = new ResponseEntity<>(result, HttpStatus.OK);
				}
				else {
					result.put("success", "fail");
					entity = new ResponseEntity<>(result, HttpStatus.OK);			
				}
			} catch (Exception e) {
				e.printStackTrace();
				result.put("success", "error");
				entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
				
			}
			return entity;
		}
		
		
	
	//마이페이지
	@GetMapping(value = "/profile")
	private ResponseEntity profile(@RequestParam String email) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			UserDto member = login.profile(email);
			if (member != null) {
				result.put("success", "success");
				result.put("User", member);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	@GetMapping(value = "/checkemail")
	private ResponseEntity checkEmail(@RequestParam String email) {
		ResponseEntity entity = null;
		//System.out.println("profile ========");
		Map result = new HashMap();
		try {
			int num = login.checkEmail(email);
			if (num == 0) {
				result.put("success", "success");
				result.put("valid", num);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	@GetMapping(value = "/checknickname")
	private ResponseEntity checkNickname(@RequestParam String nickname) {
		System.out.println("닉네임" + nickname);
		ResponseEntity entity = null;
		//System.out.println("profile ========");
		Map result = new HashMap();
		try {
			int num = login.checkNickname(nickname);
			if (num == 0) {
				result.put("success", "success");
				result.put("valid", num);
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	
	
	//관리자 권한 변경
	@PutMapping(value = "/admin")
	private ResponseEntity updateAdmin(@RequestBody Map user) {
		ResponseEntity entity = null;
		
		Map result = new HashMap();
		try {
			if (login.updateAdmin(user) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
				
			}
			else {
				result.put("success", "fail");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
			
		}
		return entity;
	}
	
	//비밀번호 암호화
	private String getHashPassword(String str) {
		String hash = null;
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(str.getBytes());
			byte byteData[] = md.digest();
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < byteData.length; i++)
				sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
			hash = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			hash = null;
		}
		return hash;
	}

}