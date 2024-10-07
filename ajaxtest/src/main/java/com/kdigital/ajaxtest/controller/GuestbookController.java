package com.kdigital.ajaxtest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kdigital.ajaxtest.dto.GuestbookDTO;
import com.kdigital.ajaxtest.service.GuestbookService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@Slf4j
public class GuestbookController {
	
	final GuestbookService service; 
	
	/**
	 * 방명록 등록
	 */
	@PostMapping("/inputGuestbook")
	public String inputGuestbook(@ModelAttribute GuestbookDTO dto) {
		log.info("방명록 정보: {}", dto.toString());
		
		service.inputGuestbook(dto);
		
		return "OK";
	}
	
	@GetMapping("/retrieveAll")
	public List<GuestbookDTO> retrieveAll() {
		
		List<GuestbookDTO> list = service.retrieveAll();
		
		return list;
	}
	
	@PostMapping("/deleteGuestbook")
	public String deleteGuestbook(
			@RequestParam(name="seq") Long seq
			, @RequestParam(name="pwd") String pwd ) {
		log.info("삭제할 글번호: {} / {} ", seq, pwd);
		service.deleteGuestbook(seq, pwd);
		
		return "OK";
	}
}









