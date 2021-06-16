package com.ssafy.a407.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a407.dto.BoardDto;
import com.ssafy.a407.dto.GroupDto;
import com.ssafy.a407.dto.UserDto;
import com.ssafy.a407.service.BoardService;
import com.ssafy.a407.service.LoginService;

@RestController
@RequestMapping("/board")
public class BoardController {
	
	@Autowired
	private BoardService board;
	
	@Autowired
	private LoginService login;
	
	//게시글 작성
	@PostMapping(value = "/create")
	private ResponseEntity create(@RequestBody BoardDto boardDto) {
		ResponseEntity entity = null;
		Map result = new HashMap();
//		System.out.println(boardDto);
		try {
			if(board.createBoard(boardDto) == 1) {
				result.put("success", "success");
			} else {
				result.put("success", "fail");
			}
			entity = new ResponseEntity(result, HttpStatus.OK);
			
		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
		}
		return entity;
	}
	
	//게시글 보기
	@GetMapping(value = "/read")
	private ResponseEntity read(@RequestParam String type, @RequestParam String word, @RequestParam(defaultValue="0") int currentPage, @RequestParam(defaultValue="0") int noticeFlag) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
//			System.out.println("controller. type : " + type + " word : " + word);
			currentPage = currentPage * 10;
			//전체 조회
			if(type.equals("")) {
				List<BoardDto> list = board.searchAll(currentPage, noticeFlag);
//		        System.out.println(list);
		        if(list != null) {
		            result.put("list", list);
		            result.put("success", "success");
		            entity = new ResponseEntity(result, HttpStatus.OK);
//		            System.out.println(entity);
		        } else {
		        	result.put("success", "fail");
		        	entity = new ResponseEntity(result, HttpStatus.OK);
		        }
			}
			//bId로 검색
			else if(type.equals("bId")) {
				List<BoardDto> list = board.searchBId(word);
//				System.out.println(list);
				if(list != null) {
					result.put("list", list);
					result.put("success", "success");
					entity = new ResponseEntity(result, HttpStatus.OK);
				} else {
		        	result.put("success", "fail");
		        	entity = new ResponseEntity(result, HttpStatus.OK);
		        }
			}
			//title로 검색
			else if(type.equals("title")) {
				List<BoardDto> list = board.searchTitle(word, currentPage, noticeFlag);
//				System.out.println(list);
				if(list != null) {
					result.put("list", list);
					result.put("success", "success");
					entity = new ResponseEntity(result, HttpStatus.OK);
				} else {
		        	result.put("success", "fail");
		        	entity = new ResponseEntity(result, HttpStatus.OK);
		        }
			}
			//작성자로 검색
			else if(type.equals("name")) {
				List<BoardDto> list = new ArrayList<BoardDto>();
				list.addAll(board.searchName(word, currentPage, noticeFlag));
//				System.out.println(list);
				if(list != null) {
					result.put("list", list);
					result.put("success", "success");
					entity = new ResponseEntity(result, HttpStatus.OK);
				} else {
		        	result.put("success", "fail");
		        	entity = new ResponseEntity(result, HttpStatus.OK);
		        }
			}
			//내용으로 검색
			else if(type.equals("contents")) {
				List<BoardDto> list = board.searchContents(word, currentPage, noticeFlag);
//				System.out.println(list);
				if(list != null) {
					result.put("list", list);
					result.put("success", "success");
					entity = new ResponseEntity(result, HttpStatus.OK);
				} else {
		        	result.put("success", "fail");
		        	entity = new ResponseEntity(result, HttpStatus.OK);
		        }
			}

		} catch (Exception e) {
			e.printStackTrace();
			result.put("success", "error");
			entity = new ResponseEntity(result, HttpStatus.OK);
		}
		return entity;
	}
	
	//게시글 수정
	@PutMapping(value = "/update")
	private ResponseEntity update(@RequestBody Map mem) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
//			System.out.println(mem);
			if (board.update(mem) == 1) {
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
	
	//게시글 삭제
	@DeleteMapping(value = "/delete")
	private ResponseEntity delete(@RequestHeader int bId) {
		ResponseEntity entity = null;
		Map result = new HashMap();
		try {
			if (board.delete(bId) == 1) {
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
	
	@GetMapping(value = "/countboard")
	private ResponseEntity countBoard(@RequestParam String email) {
		ResponseEntity entity = null;
		//System.out.println("profile ========");
		Map result = new HashMap();
		try {
			int num = board.countBoard(email);
			if (num > -1) {
				result.put("success", "success");
				result.put("count", num);
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
	

}