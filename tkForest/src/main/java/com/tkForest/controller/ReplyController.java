package com.tkForest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tkForest.dto.ReplyDTO;
import com.tkForest.service.ReplyService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/reply")
@RequiredArgsConstructor
public class ReplyController {
	
	final ReplyService replyService;
	
	@PostMapping("/replyInsert")
	public ReplyDTO replyInsert(@ModelAttribute ReplyDTO replyDTO) {
		log.info("댓글 삽입: {}", replyDTO.toString());
	
		replyService.replyInsert(replyDTO);
		
		return replyDTO; 
		
	}
	
	/**
	 *  전달받은 게시글의 댓글목록 전체를 조회
	 * @return
	 */
	@GetMapping("/replyAll") 
	public List<ReplyDTO> replyAll(@RequestParam(name="inquiryNo") Integer inquiryNo) {
		log.info("{}", inquiryNo);
		
		List<ReplyDTO> list = replyService.replyAll(inquiryNo);
		
		return list;
	}
	
}
