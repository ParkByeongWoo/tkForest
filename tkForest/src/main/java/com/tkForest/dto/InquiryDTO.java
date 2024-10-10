package com.tkForest.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.tkForest.entity.InquiryEntity;

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
public class InquiryDTO {
	private Integer inquiryNo;
	private Integer productNo;
	private String buyerMemberNo;
	private String sellerMemberNo;
	private String subject;
	private String contents;
	private LocalDateTime offerSendDate;
	private LocalDateTime offerExpireDate;
	private Integer orderQuantity;
	private String orderUnitEtc;
	private Double expectedPrice;
	
	// 업로드 하는 파일을 받는 멤버변수 
	private MultipartFile uploadFile;
		
	private String originalFileName;	// 파일의 원래 파일명
	private String savedFileName;		// 하드디스크에 저장될 때 사용되는 변경된 파일명

	// 생성자 ==> 페이징을 처리를 위해 BoardService.java에서 Page형태로 받은 데이터 중
	// 목록에 출력할 멤버만 간추리기 위해 만든 생성자
	public InquiryDTO(Integer inquiryNo
	        , Integer productNo
	        , String buyerMemberNo
	        , String sellerMemberNo
	        , String subject
	        , String contents
	        , LocalDateTime offerSendDate
	        , LocalDateTime offerExpireDate
	        , Integer orderQuantity
	        , String orderUnitEtc
	        , Double expectedPrice
	        , String originalFileName) {
	    this.inquiryNo = inquiryNo;
	    this.productNo = productNo;
	    this.buyerMemberNo = buyerMemberNo;
	    this.sellerMemberNo = sellerMemberNo;
	    this.subject = subject;
	    this.contents = contents;
	    this.offerSendDate = offerSendDate;
	    this.offerExpireDate = offerExpireDate;
	    this.orderQuantity = orderQuantity;
	    this.orderUnitEtc = orderUnitEtc;
	    this.expectedPrice = expectedPrice;
	    this.originalFileName = originalFileName;
	}
	
	// Entity를 받아서 ----> DTO로 반환 
	public static InquiryDTO toDTO(InquiryEntity inquiryEntity) {
	    return InquiryDTO.builder()
	            .inquiryNo(inquiryEntity.getInquiryNo())
	            .productNo(inquiryEntity.getProductNo())
	            .buyerMemberNo(inquiryEntity.getBuyerMemberNo())
	            .sellerMemberNo(inquiryEntity.getSellerMemberNo())
	            .subject(inquiryEntity.getSubject())
	            .contents(inquiryEntity.getContents())
	            .offerSendDate(inquiryEntity.getOfferSendDate())
	            .offerExpireDate(inquiryEntity.getOfferExpireDate())
	            .orderQuantity(inquiryEntity.getOrderQuantity())
	            .orderUnitEtc(inquiryEntity.getOrderUnitEtc())
	            .expectedPrice(inquiryEntity.getExpectedPrice())
	            .originalFileName(inquiryEntity.getOriginalFileName())
	            .savedFileName(inquiryEntity.getSavedFileName())
	            .build();
	}

}
