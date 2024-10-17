package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.S_InterestDTO;

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

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@ToString
@Builder

@Entity
@Table(name = "S_INTEREST")
public class S_InterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERESTNO")
    private Integer interestNo;

    // From Seller (외래키로 SellerEntity 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_SELLER_MEMBERNO_INTEREST", referencedColumnName = "SELLER_MEMBERNO", nullable = false)
    private SellerEntity interestfromSellerEntity; // 즐겨찾기 누르는 셀러

    // To Seller (외래키로 SellerEntity 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_SELLER_MEMBERNO_INTEREST", referencedColumnName = "SELLER_MEMBERNO")
    private SellerEntity interestedSellerEntity; //즐겨찾기 받는 셀러

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_PRODUCTNO_INTEREST", referencedColumnName = "PRODUCTNO")
    private ProductEntity interestedProductEntity; // 즐겨찾기 받는 상품

    @Column(name = "INTEREST_CREATEDDATE", nullable = false)
    @CreationTimestamp  // 자동으로 현재 시간을 설정
    private LocalDateTime interestCreatedDate;

    @Column(name = "INTEREST_USEYN", nullable = false, length = 1)
    private String interestUseYn;

    // DTO -> Entity 변환 메서드

    public static S_InterestEntity toEntity(S_InterestDTO s_InterestDTO, SellerEntity fromSellerEntity, SellerEntity toSellerEntity, ProductEntity interestedProductEntity) {
        return S_InterestEntity.builder()
                .interestNo(s_InterestDTO.getInterestNo())
                .interestfromSellerEntity(fromSellerEntity)  // 외래키로 SellerEntity 매핑
                .interestedSellerEntity(toSellerEntity)      // 외래키로 SellerEntity 매핑
                .interestedProductEntity(interestedProductEntity)
                .interestCreatedDate(s_InterestDTO.getInterestCreatedDate())
                .interestUseYn(s_InterestDTO.getInterestUseYn())
                .build();
    }
}

