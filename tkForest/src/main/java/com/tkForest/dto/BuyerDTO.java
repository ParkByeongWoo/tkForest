package com.tkForest.dto;

import java.time.LocalDateTime;

import com.tkForest.entity.BuyerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
public class BuyerDTO {
    private String buyerMemberNo;
    private LocalDateTime buyerMemberJoinDate;
    private String companyName;
    private String bizPhoneNumber;
    private String companyDescription;
    private String picName; 
    private String phoneNumber;
    private String id;
    private String password;
    private String email;
    private String concernKeyword;
    private Boolean buyerStatus;
    private String nationCode;

    // Entity => DTO 변환하는 메소드
    public static BuyerDTO toDTO(BuyerEntity buyerEntity) {
        return BuyerDTO.builder()
                .buyerMemberNo(buyerEntity.getBuyerMemberNo())
                .buyerMemberJoinDate(buyerEntity.getBuyerMemberJoinDate())
                .companyName(buyerEntity.getCompanyName())
                .bizPhoneNumber(buyerEntity.getBizPhoneNumber())
                .companyDescription(buyerEntity.getCompanyDescription())
                .picName(buyerEntity.getPicName()) 
                .phoneNumber(buyerEntity.getPhoneNumber())
                .id(buyerEntity.getId())
                .password(buyerEntity.getPassword())
                .email(buyerEntity.getEmail())
                .concernKeyword(buyerEntity.getConcernKeyword())
                .buyerStatus(buyerEntity.getBuyerStatus())
                .nationCode(buyerEntity.getNationCode())
                .build();
    }
}
