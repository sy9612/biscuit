package com.ssafy.a407Chat.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.a407Chat.dto.ChatMessageDto;
import com.ssafy.a407Chat.dto.ChatRoomDto;
import com.ssafy.a407Chat.service.ChatService;

import lombok.RequiredArgsConstructor;


@RequiredArgsConstructor
@RestController
@RequestMapping("/chat")
public class ChatRoomController {
	@Autowired
	private ChatService chatService;
	
	@GetMapping(value = "/test")
	public String test() {
		return "test";
	}
	
	@GetMapping(value = "/rooms")
	public ResponseEntity roomList(){
		
		Map result = new HashMap();
        ResponseEntity entity = null;
        List<ChatRoomDto> data;
		try {
			data = chatService.findAllRoom();
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
	
	@PostMapping(value = "/room")
	public ResponseEntity createRoom(@RequestBody ChatRoomDto dto) {
		System.out.println("create room. : " + dto.getRoomName());


		Map result = new HashMap();
        ResponseEntity entity = null;

        ChatRoomDto data;
		try {
			data = chatService.createRoom(dto);

			result.put("data", data);
			result.put("success", "success");
			entity = new ResponseEntity<>(result, HttpStatus.OK);
		} catch (Exception e) {


			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}

			
		return entity;
	}
	
	@GetMapping(value = "/search")
	public ResponseEntity roomInfo(@RequestParam String roomId) {
		System.out.println("roomId : " + roomId);
		Map result = new HashMap();
        ResponseEntity entity = null;
        ChatRoomDto data;
		try {
			data = chatService.findRoomById(roomId);

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
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return entity;
	}
	
	@GetMapping(value = "/messages")
	public ResponseEntity messages(@RequestParam String roomId) {
		System.out.println("roomId : " + roomId);
		Map result = new HashMap();
        ResponseEntity entity = null;
        List<ChatMessageDto> data;
		try {
			data = chatService.loadMessages(roomId);

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
			result.put("success", "error");
			entity = new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
			e.printStackTrace();
		}
		return entity;
	}
	
	
}
