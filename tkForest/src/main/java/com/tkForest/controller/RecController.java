package com.tkForest.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tkForest.service.RecService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/rec")
@RequiredArgsConstructor
public class RecController {
	
	final RecService recService;
	
	@GetMapping("/recPage")
	public String recPage() {
		log.info("상품 추천 페이지로 넘어감");
		
		return "rec/recPage";
	}
	
	@PostMapping("/recList")
	@ResponseBody
	public Map<String, Object> recList(@ModelAttribute String buyerMemeberNo) {
		
		Map<String, Object> result = recService.recList(buyerMemeberNo);
		
		
		return result;
	}
}
