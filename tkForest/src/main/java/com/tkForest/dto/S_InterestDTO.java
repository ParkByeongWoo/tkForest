package com.tkForest.dto;

import java.time.LocalDateTime;

import com.tkForest.entity.S_InterestEntity;

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
public class S_InterestDTO {

    private Integer interestNo;
    private String fromSellerMemberNoInterest;
    private String toSellerMemberNoInterest;
    private Integer toProductNoInterest;
    private LocalDateTime interestCreatedDate;
    private String interestUseYn;

    // Entity -> DTO 변환 메서드
    public static S_InterestDTO toDTO(S_InterestEntity s_InterestEntity) {
        return S_InterestDTO.builder()
                .interestNo(s_InterestEntity.getInterestNo())
                .fromSellerMemberNoInterest(s_InterestEntity.getFromSellerMemberNoInterest())
                .toSellerMemberNoInterest(s_InterestEntity.getToSellerMemberNoInterest())
                .toProductNoInterest(s_InterestEntity.getToProductNoInterest())
                .interestCreatedDate(s_InterestEntity.getInterestCreatedDate())
                .interestUseYn(s_InterestEntity.getInterestUseYn())
                .build();
    }
}
