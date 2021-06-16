package com.ssafy.a407.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ssafy.a407.service.EmailService;
import com.ssafy.a407.service.EmailServiceImpl;


@Controller
@RequestMapping("/service/*")
public class MailController {
	@Autowired
	EmailService service;

	private static final Logger logger = LoggerFactory.getLogger(MailController.class);

	@PostMapping("/mail")
	@ResponseBody
	public ResponseEntity emailConfirm(@RequestBody String userId){
		
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		logger.info("post emailConfirm");
		
		userId = userId.substring(0, userId.length()-1);
		userId= userId.replace("%40", "@");	
		
//		System.out.println("전달 받은 이메일 : " + userId);
		try {
			service.sendSimpleMessage(userId);
			result.put("success", "success");
			entity = new ResponseEntity(result, HttpStatus.OK);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@PostMapping("/verifyCode")
	@ResponseBody
	public ResponseEntity verifyCode(@RequestBody String code) {
		logger.info("Post verifyCode");

		ResponseEntity entity = null;
		Map result = new HashMap();
		code = code.substring(0, code.length()-1);
//		int result = 0;
//		System.out.println("code : " + code);
//		System.out.println("code match : " + EmailServiceImpl.ePw.equals(code));
		if (EmailServiceImpl.ePw.equals(code)) {
			result.put("success", "success");
			entity = new ResponseEntity(result, HttpStatus.OK);
		}else {
        	result.put("success", "fail");
        	entity = new ResponseEntity(result, HttpStatus.OK);
		}

		return entity;
	}
	
}