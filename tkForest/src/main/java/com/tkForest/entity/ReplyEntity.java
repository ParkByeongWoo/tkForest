package com.tkForest.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.ReplyDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
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

@Entity
@Table(name="INQUIRY_REPLY")
public class ReplyEntity {
	@Id
	@Column(name="REPLYNO")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replyNo;
	
	/*
	 * Inquiry: Reply의 관계 ==> 1 : 다
	 * 댓글이 다의 위치, inquiryNo은 Join컬럼
	 */
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="INQUIRYNO", referencedColumnName = "INQUIRYNO", nullable = false)
	private InquiryEntity inquiryEntity;
	
	@Column(name="REPLY_PARENT") // 부모 답변 번호 (대답변)
	private Integer replyParent;   
	
	@Column(name="REPLY_CONTENTS")
    private String replyContents;
    
	@Column(name="REPLY_DATE")
    @CreationTimestamp
    private LocalDateTime replyDate;
	
	@Column(name="REPLY_WRITER")
    private String replyWriter;  
	
	@Column(name="REPLY_WRITER_TYPE")
    private String replyWriterType;   
	
	
	// DTO --> Entity 반환
	public static ReplyEntity toEntity(ReplyDTO dto, InquiryEntity inquiryEntity) {
		return ReplyEntity.builder()
				.replyNo(dto.getReplyNo())
				.replyParent(dto.getReplyParent())
				.replyContents(dto.getReplyContents())
				.replyDate(dto.getReplyDate())
				.replyWriter(dto.getReplyWriter())
				.replyWriterType(dto.getReplyWriterType())
				.inquiryEntity(inquiryEntity)
				.build();
	}
}
