package com.tkForest.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.tkForest.entity.ReplyEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class ReplyDTO {
    private Integer replyNo;           // 답변 고유번호
    private Integer inquiryNo;         // 인콰이어리 고유번호
    private Integer replyParent;       // 상위 답변 고유번호 (nullable)
    private String replyContents;      // 답변 내용
    private LocalDateTime replyDate;   // 답변 작성일
    private String replyWriter;        // 답변 작성자
    private String replyWriterType;    // 답변 작성자 유형 ('BUYER' 또는 'SELLER')


    
	// Entity --> DTO
	public static ReplyDTO toDTO(ReplyEntity entity, Integer inquiryNo) {
		return ReplyDTO.builder()
				.replyNo(entity.getReplyNo())
				.replyParent(entity.getReplyParent())
				.inquiryNo(inquiryNo)
				.replyContents(entity.getReplyContents())
				.replyDate(entity.getReplyDate())
				.replyWriter(entity.getReplyWriter())
				.replyWriterType(entity.getReplyWriterType())
				.build();
	}
}

