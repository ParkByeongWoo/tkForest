package com.kdigital.ajaxtest.controller;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class AjaxController {
	// 첫 번째 요청
	@GetMapping("/ajaxReq1")
	@ResponseBody
	public String ajaxReq1() {
		System.out.println("Req1 요청 수신");
		
		return "OK";  // OK.html이 아니라 문자데이터 "OK"를 응답
	}
	
	// 두 번째 요청
	@GetMapping("/ajaxReq2")
	@ResponseBody
	public Map<String, String> ajaxReq2(
			@RequestParam(name="name") String name
			, @RequestParam(name="phone") String phone
			) {
		
		Map<String, String> map = new HashMap<>();
		map.put("name", name+"님");
		map.put("phone", phone);
		
		return map;
	}
	
	// 세 번째 요청(
	@GetMapping("/ajaxReq3")
	@ResponseBody
	public List<String> ajaxReq3() {
		List<String> list = Arrays.asList("사과", "복숭아", "메론", "수박", "딸기");

		
		return list;
	}
}



