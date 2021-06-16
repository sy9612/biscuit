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
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a407.dto.ScheduleDto;
import com.ssafy.a407.service.ScheduleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/schedule")
@Api("ScheduleController")
public class ScheduleController {
	
	@Autowired
	private ScheduleService service;
	
	public static final Logger logger = LoggerFactory.getLogger(ScheduleController.class);
	
	/* * pgFlag - 0 : 개인 일정, 1 : 그룹 일정
		* email - 그룹 일정 시 null
		* gId - 개인 일정 시 null 						*/
	@ApiOperation(httpMethod = "POST"
	,value = "스케줄 생성"
	,notes = "스케줄 생성하는 API"
	,response = Integer.class
	,responseContainer = "ArrayList")
	@PostMapping(value = "/create")
    private ResponseEntity register(@RequestBody @ApiParam(value = "필요한 정보 : sId빼고 다", required = true) ScheduleDto schedule) {
//		System.out.println("[controller] Schedule create >> "+ schedule.toString());
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			if(service.createSchedule(schedule) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}else {
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
	
	@GetMapping(value = "/detail")
	private ResponseEntity scheduleDetail(@RequestParam int sId) {
//		System.out.println("[controller] Schedule detail sId >> "+ sId);
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			ScheduleDto schedule = service.searchDetail(sId);
			if(schedule != null) {
//				System.out.println("return schedule >> " + schedule.toString());
				result.put("Schedule", schedule);
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);	
			}else {
	        	result.put("success", "fail");
	        	entity = new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}		
		//System.out.println(entity.getBody().toString());
		return entity;
	}
	
	@GetMapping(value = "/grouplist")
	private ResponseEntity groupScheduleList(@RequestParam int gId) {
//		System.out.println("[controller] Schedule detail sId >> "+ sId);
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			List<ScheduleDto> list = service.searchGroupSchedule(gId);
			if(list != null) {
//				System.out.println("return schedule >> " + schedule.toString());
				result.put("list", list);
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);	
			}else {
	        	result.put("success", "fail");
	        	entity = new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}		
		//System.out.println(entity.getBody().toString());
		return entity;
	}
	
	@GetMapping(value = "/perlist")
	private ResponseEntity personalScheduleList(@RequestParam String email) {
//		System.out.println("[controller] Schedule detail sId >> "+ sId);
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			List<ScheduleDto> list = service.searchPersonalSchedule(email);
			if(list != null) {
//				System.out.println("return schedule >> " + schedule.toString());
				result.put("list", list);
				result.put("success", "success");
				entity = new ResponseEntity<>(result, HttpStatus.OK);	
			}else {
	        	result.put("success", "fail");
	        	entity = new ResponseEntity(result, HttpStatus.OK);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			result.put("success", "error"); 
	        entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}		
		//System.out.println(entity.getBody().toString());
		return entity;
	}
	
	@PutMapping(value = "/update")
	private ResponseEntity update(@RequestBody ScheduleDto schedule) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			if(service.modifySchedule(schedule) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}else {
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
	
	@DeleteMapping(value = "/delete")
	private ResponseEntity delete(@RequestParam int sId) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			if(service.removeSchedule(sId) == 1) {
				result.put("success", "success");
				entity = new ResponseEntity(result, HttpStatus.OK);
			}else {
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
}
