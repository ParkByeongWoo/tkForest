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

    // 외래 키를 사용하기보다는 관련된 DTO 객체를 사용
    private ProductDTO product;      // ProductDTO로 변경
    private BuyerDTO buyer;          // BuyerDTO로 변경
    private SellerDTO seller;        // SellerDTO로 변경
    
    private String subject;
    private String contents;
    private LocalDateTime offerSendDate;
    private LocalDateTime offerExpireDate;
    private Integer orderQuantity;
    private String orderUnitEtc;
    private Double expectedPrice;
    
    private MultipartFile uploadFile;
    private String originalFileName;
    private String savedFileName;

    // Entity -> DTO 변환 메서드
    public static InquiryDTO toDTO(InquiryEntity inquiryEntity, ProductDTO productDTO, BuyerDTO buyerDTO, SellerDTO sellerDTO) {
        return InquiryDTO.builder()
                .inquiryNo(inquiryEntity.getInquiryNo())
                .product(productDTO)  // ProductDTO로 매핑
                .buyer(buyerDTO)      // BuyerDTO로 매핑
                .seller(sellerDTO)    // SellerDTO로 매핑
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

