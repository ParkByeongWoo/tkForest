package com.tkForest.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public ReplyDTO replyInsert(@RequestBody ReplyDTO replyDTO) {
        log.info("댓글 삽입: {}", replyDTO.toString());
        return replyService.replyInsert(replyDTO);  // 댓글 작성 후 DTO 반환
    }

    /**
     * 전달받은 인콰이어리의 댓글 목록 전체 조회
     */
    @GetMapping("/replyAll")
    public List<ReplyDTO> replyAll(@RequestParam(name = "inquiryNo") Integer inquiryNo) {
        log.info("댓글 목록 조회 - inquiryNo: {}", inquiryNo);
        return replyService.replyAll(inquiryNo);
    }
}


