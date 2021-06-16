package com.ssafy.a407Chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a407Chat.dto.ChatRoomDto;
import com.ssafy.a407Chat.dto.ChatRoomMemberDto;
import com.ssafy.a407Chat.service.ChatService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomMemberController {

	@Autowired
	private ChatService chatService;
	
	
	@PostMapping(value = "/member")
	public ResponseEntity createRoom(@RequestBody ChatRoomMemberDto dto) {


		Map result = new HashMap();
        ResponseEntity entity = null;
		try {
			chatService.InviteMember(dto);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {


			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

			
		return entity;
	}
	
	@GetMapping(value = "/myroom")
	public ResponseEntity myroom(@RequestParam String email){
		
		Map result = new HashMap();
        ResponseEntity entity = null;
        List<ChatRoomDto> data;
		try {
			data = chatService.findMyRoom(email);
			if(data != null) {

				result.put("data", data);
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
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entity;
	}
	
	@GetMapping(value = "/member")
	public ResponseEntity memberList(@RequestParam String roomId){
		
		Map result = new HashMap();
        ResponseEntity entity = null;
        List<ChatRoomMemberDto> data;
		try {
			data = chatService.findAllRoomMember(roomId);
			if(data != null) {

				result.put("data", data);
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
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return entity;
	}
	
	@PutMapping(value = "/join")
	public ResponseEntity join(@RequestBody Map map) {


		Map result = new HashMap();
        ResponseEntity entity = null;
		try {
			chatService.joinRoom(map);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {


			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

			
		return entity;
	}
}
