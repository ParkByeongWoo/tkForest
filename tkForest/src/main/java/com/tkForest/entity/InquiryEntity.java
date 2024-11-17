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

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name = "INQUIRY")
@EntityListeners(AuditingEntityListener.class)
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INQUIRYNO")
    private Integer inquiryNo;

    // 외래 키 설정 (ProductEntity와 Many-to-One 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTNO", referencedColumnName = "PRODUCTNO")
    private ProductEntity productEntity;

    // 외래 키 설정 (BuyerEntity와 Many-to-One 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_MEMBERNO", referencedColumnName = "BUYER_MEMBERNO")
    private BuyerEntity buyerEntity;

    // 외래 키 설정 (SellerEntity와 Many-to-One 관계)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_MEMBERNO", referencedColumnName = "SELLER_MEMBERNO")
    private SellerEntity sellerEntity;

    @Column(name = "SUBJECT")
    private String subject;

    @Column(name = "CONTENTS")
    private String contents;

    @Column(name = "OFFERSENDDATE")
    @CreationTimestamp
    private LocalDateTime offerSendDate;

    @Column(name = "OFFEREXPIREDATE")
    private LocalDateTime offerExpireDate;

    @Column(name = "ORDERQUANTITY")
    private Integer orderQuantity;

    @Column(name = "ORDERUNITETC")
    private String orderUnitEtc;

    @Column(name = "EXPECTEDPRICE")
    private Double expectedPrice;
    
    @Column(name = "ORDER_UNIT")
    private String orderUnit;
    
    // 첨부파일이 있을 경우 추가
    @Column(name = "ORIGINAL_FILE_NAME")
    private String originalFileName;

    @Column(name = "SAVED_FILE_NAME")
    private String savedFileName;

      
   // 댓글 개수 처리
   @Formula("(SELECT count(1) FROM INQUIRY_REPLY WHERE INQUIRYNO  = INQUIRY_REPLY.INQUIRYNO)")
   private Integer replyCount;
   
   // Entity를 받아서 ----> DTO로 반환 
   public static InquiryEntity toEntity(InquiryDTO inquiryDTO, ProductEntity productEntity, BuyerEntity buyerEntity, SellerEntity sellerEntity) {
       return InquiryEntity.builder()
               .inquiryNo(inquiryDTO.getInquiryNo())
               .productEntity(productEntity)  // 매개변수로 받은 값을 사용
               .buyerEntity(buyerEntity)
               .sellerEntity(sellerEntity)
               .subject(inquiryDTO.getSubject())
               .contents(inquiryDTO.getContents())
               .offerSendDate(inquiryDTO.getOfferSendDate())
               .offerExpireDate(inquiryDTO.getOfferExpireDate())
               .orderQuantity(inquiryDTO.getOrderQuantity())
               .orderUnit(inquiryDTO.getOrderUnit())
               .orderUnitEtc(inquiryDTO.getOrderUnitEtc())
               .expectedPrice(inquiryDTO.getExpectedPrice())
               .originalFileName(inquiryDTO.getOriginalFileName())
               .savedFileName(inquiryDTO.getSavedFileName())
               .build();
   }

public Integer getExpirationOption() {
	// TODO Auto-generated method stub
	return null;
}

}
