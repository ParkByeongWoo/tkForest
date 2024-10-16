package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.S_InterestDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "S_INTEREST")
public class S_InterestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "INTERESTNO")
    private Integer interestNo;

    @Column(name = "FROM_SELLER_MEMBERNO_INTEREST", length = 10)
    private String fromSellerMemberNoInterest;

    @Column(name = "TO_SELLER_MEMBERNO_INTEREST", length = 10)
    private String toSellerMemberNoInterest;

    @Column(name = "TO_PRODUCTNO_INTEREST")
    private Integer toProductNoInterest;

    @Column(name = "INTEREST_CREATEDDATE", nullable = false)
    @CreationTimestamp  // 자동으로 현재 시간을 설정
    private LocalDateTime interestCreatedDate;

    @Column(name = "INTEREST_USEYN", nullable = false, length = 1)
    private String interestUseYn;

    // DTO -> Entity 변환 메서드
    public static S_InterestEntity toEntity(S_InterestDTO s_InterestDTO) {
        return S_InterestEntity.builder()
                .interestNo(s_InterestDTO.getInterestNo())
                .fromSellerMemberNoInterest(s_InterestDTO.getFromSellerMemberNoInterest())
                .toSellerMemberNoInterest(s_InterestDTO.getToSellerMemberNoInterest())
                .toProductNoInterest(s_InterestDTO.getToProductNoInterest())
                .interestCreatedDate(s_InterestDTO.getInterestCreatedDate())
                .interestUseYn(s_InterestDTO.getInterestUseYn())
                .build();
    }
}
