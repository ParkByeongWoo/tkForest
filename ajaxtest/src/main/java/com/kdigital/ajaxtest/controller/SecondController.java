package com.kdigital.ajaxtest.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kdigital.ajaxtest.dto.CustomerDTO;

@Controller
public class SecondController {
	@GetMapping("/secondpage")
	public String secondpage() {
		return "second";
	}
	
	@GetMapping("/receive")
	@ResponseBody
	public List<CustomerDTO> receive() {
		
		List<CustomerDTO> list = Arrays.asList(
				new CustomerDTO("홍길동", "hong@naver.com", "010-1111-2222")
				, new CustomerDTO("전우치", "woochi@naver.com", "010-2222-1234")
				, new CustomerDTO("임꺽정", "Limjung@naver.com", "010-3333-5678")
				, new CustomerDTO("손오공","gonggong@naver.com", "010-4444-1000")
				, new CustomerDTO("사오정", "ojung@naver.com", "010-5555-2301" )
		);
		
		return list;
	}
}
