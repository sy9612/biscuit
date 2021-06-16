package com.ssafy.a407.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a407.dto.NotificationDto;
import com.ssafy.a407.service.NotificationService;

import io.swagger.annotations.Api;

@RestController
@RequestMapping("/notification")
@Api("NotificationController")
public class NotificationController {

	@Autowired
	private NotificationService service;

	public static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@PostMapping(value = "/create")
	private ResponseEntity register(@RequestBody NotificationDto notification) {

		ResponseEntity entity = null;
		Map result = new HashMap();
//		System.out.println("hi " + notification.getReceiveEmail());
		try {
			if (service.createNotification(notification) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}

	@GetMapping(value = "/read")
	private ResponseEntity notificationList(@RequestParam String receiveEmail) {
		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			List<NotificationDto> list = service.searchPersonalNotification(receiveEmail);
			if (list != null) {
				result.put("list", list);
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}

		return entity;
	}

	@DeleteMapping(value = "/delete")
	private ResponseEntity delete(@RequestParam int nId) {

		ResponseEntity entity = null;
		Map result = new HashMap();

		try {
			if (service.removeNotification(nId) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			} else {
				result.put("success", "fail");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	@PutMapping(value = "/update")
	private ResponseEntity update(@RequestBody NotificationDto notification) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			if(service.modifyNotification(notification) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}else {
				result.put("success", "fail");
	        	entity = new ResponseEntity(result, HttpStatus.OK);
			}
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
}
