package com.tkForest.dto;

import java.time.LocalDateTime;
import java.util.List;

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
    private String buyerId;
    private String password;
    private String email;
    private String concernKeyword;
    private Boolean buyerStatus;
    private String nationCode;  // NationDTO 대신 nationCode로 변경
    
    private List<String> categoryNames;

    // Entity => DTO 변환 메소드
    public static BuyerDTO toDTO(BuyerEntity buyerEntity) {
        return BuyerDTO.builder()
                .buyerMemberNo(buyerEntity.getBuyerMemberNo())
                .buyerMemberJoinDate(buyerEntity.getBuyerMemberJoinDate())
                .companyName(buyerEntity.getCompanyName())
                .bizPhoneNumber(buyerEntity.getBizPhoneNumber())
                .companyDescription(buyerEntity.getCompanyDescription())
                .picName(buyerEntity.getPicName()) 
                .phoneNumber(buyerEntity.getPhoneNumber())
                .buyerId(buyerEntity.getBuyerId())
                .password(buyerEntity.getPassword())
                .email(buyerEntity.getEmail())
                .concernKeyword(buyerEntity.getConcernKeyword())
                .buyerStatus(buyerEntity.getBuyerStatus())
                .nationCode(buyerEntity.getNationCode())  // NationCode 매핑
                .build();
    }
}
