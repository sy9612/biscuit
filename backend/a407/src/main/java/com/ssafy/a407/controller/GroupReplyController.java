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

import com.ssafy.a407.dto.GroupReplyDto;
import com.ssafy.a407.service.GroupReplyService;
import com.ssafy.a407.util.Pagination;

@RestController
@RequestMapping("/greply")
public class GroupReplyController {
	
	@Autowired
	private GroupReplyService service;
	
	public static final Logger logger = LoggerFactory.getLogger(ReplyController.class);
	
	@PostMapping(value = "/create")
    private ResponseEntity register(@RequestBody GroupReplyDto reply) {
//		System.out.println("[controller] registReply >> "+ reply.toString());
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			if(service.registReply(reply) == 1) {
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

	@PutMapping(value = "/update")
    private ResponseEntity update(@RequestBody GroupReplyDto reply) {
//		System.out.println("수정 " +reply);
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			if(service.modifyReply(reply) == 1) {
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
	private ResponseEntity delete(@RequestParam int rId) {
//		System.out.println("delete >> "+rId);
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		try {
			if(service.removeReply(rId) == 1) {
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
	
	
	@GetMapping(value = "/list")
	private ResponseEntity getReplyList(@RequestParam int page, @RequestParam int bId) {
//		System.out.println("[controller] Reply List >> page : "+page+"  / bId  : "+bId);
		Map tmp = new HashMap();

		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			//전체 댓글 갯수
			int listCnt = service.getReplyListCnt(bId);
//			System.out.println("listCnt >> " + listCnt);
			//페이징 객체 생성
			Pagination pagination = new Pagination(listCnt,page);
			//pagination.pageInfo(page,20, listCnt);
			
			Map pageMap = new HashMap();
			pageMap.put("startIndex", pagination.getStartIndex());
			pageMap.put("pageSize", pagination.getPageSize());
			pageMap.put("bId",bId);
			
			List<GroupReplyDto> list = service.getReplyList(pageMap);
			if(list != null) {
			
				result.put("success", "success");
				result.put("pagination", pagination);
				result.put("list", list);
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
		return entity;
	}
}
