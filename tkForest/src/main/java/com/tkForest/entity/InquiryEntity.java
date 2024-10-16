package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tkForest.dto.InquiryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name="inquiry")
@EntityListeners(AuditingEntityListener.class)
public class InquiryEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="INQUIRYNO")
	private Integer inquiryNo;
	
	@Column(name="PRODUCTNO")
	private Integer productNo;
	
	@Column(name="BUYER_MEMBERNO")
	private String buyerMemberNo;
	
	@Column(name="SELLER_MEMBERNO")
	private String sellerMemberNo;
	
	@Column(name="SUBJECT")
	private String subject;
	
	@Column(name="CONTENTS")
	private String contents;
	
	@Column(name="OFFERSENDDATE")
	@CreationTimestamp
	private LocalDateTime offerSendDate;
	
	@Column(name="OFFEREXPIREDATE")
	/* 만료일자 expiredate.js*/
	private LocalDateTime offerExpireDate;
	
	@Column(name="ORDERQUANTITY")
	private Integer orderQuantity;
	
	@Column(name="ORDERUNITETC")
	private String orderUnitEtc;
	
	@Column(name="EXPECTEDPRICE")
	private Double expectedPrice;
	
	// 첨부파일이 있을 경우 추가
	@Column(name="original_file_name")
	private String originalFileName;
		
	@Column(name="saved_file_name")
	private String savedFileName;
		
	// 댓글 개수 처리
	@Formula("(SELECT count(1) FROM INQUIRY_REPLY WHERE INQUIRYNO  = INQUIRY_REPLY.INQUIRYNO)")
	private Integer replyCount;
	
	// Entity를 받아서 ----> DTO로 반환 
	public static InquiryEntity toEntity(InquiryDTO inquiryDTO) {
		return InquiryEntity.builder()
		            .inquiryNo(inquiryDTO.getInquiryNo())
		            .productNo(inquiryDTO.getProductNo())
		            .buyerMemberNo(inquiryDTO.getBuyerMemberNo())
		            .sellerMemberNo(inquiryDTO.getSellerMemberNo())
		            .subject(inquiryDTO.getSubject())
		            .contents(inquiryDTO.getContents())
		            .offerSendDate(inquiryDTO.getOfferSendDate())
		            .offerExpireDate(inquiryDTO.getOfferExpireDate())
		            .orderQuantity(inquiryDTO.getOrderQuantity())
		            .orderUnitEtc(inquiryDTO.getOrderUnitEtc())
		            .expectedPrice(inquiryDTO.getExpectedPrice())
		            .originalFileName(inquiryDTO.getOriginalFileName())
		            .savedFileName(inquiryDTO.getSavedFileName())
		            .build();
		}
}
