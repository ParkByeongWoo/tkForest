package com.tkForest.dto;

import java.time.LocalDateTime;

import com.tkForest.entity.SellerEntity;

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
public class SellerDTO {
    private String sellerMemberNo;
    private LocalDateTime sellerMemberJoinDate;
    private String companyName;
    private String address;
    private String bizRegNo;
    private String ownerName;
    private String bizPhoneNumber;
    private String companyDescription;
    private String picName;
    private String phoneNumber;
    private String id;
    private String password;
    private String email;
    private String sellerKeyword;
    private Boolean sellerStatus;

    // Entity => DTO 변환하는 메소드
    public static SellerDTO toDTO(SellerEntity sellerEntity) {
        return SellerDTO.builder()
                .sellerMemberNo(sellerEntity.getSellerMemberNo())
                .sellerMemberJoinDate(sellerEntity.getSellerMemberJoinDate())
                .companyName(sellerEntity.getCompanyName())
                .address(sellerEntity.getAddress())
                .bizRegNo(sellerEntity.getBizRegNo())
                .ownerName(sellerEntity.getOwnerName())
                .bizPhoneNumber(sellerEntity.getBizPhoneNumber())
                .companyDescription(sellerEntity.getCompanyDescription())
                .picName(sellerEntity.getPicName())
                .phoneNumber(sellerEntity.getPhoneNumber())
                .id(sellerEntity.getId())
                .password(sellerEntity.getPassword())
                .email(sellerEntity.getEmail())
                .sellerKeyword(sellerEntity.getSellerKeyword())
                .sellerStatus(sellerEntity.getSellerStatus())
                .build();
    }
}
