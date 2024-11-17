package com.tkForest.dto;

import java.time.LocalDateTime;
import java.util.List;

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
    private String bizregNo;
    private String ownerName;
    private String bizPhoneNumber;
    private String companyDescription;
    private String picName;
    private String phoneNumber;
    private String sellerId;
    private String password;
    private String email;
    private String sellerKeyword;
    private Boolean sellerStatus;
    
    // private String categoryName;
    private List<String> categoryNames;
    private List<Integer> sellerCertificateTypeCodes;

    // Entity => DTO 변환하는 메소드
    public static SellerDTO toDTO(SellerEntity sellerEntity) {
        return SellerDTO.builder()
                .sellerMemberNo(sellerEntity.getSellerMemberNo())
                .sellerMemberJoinDate(sellerEntity.getSellerMemberJoinDate())
                .companyName(sellerEntity.getCompanyName())
                .address(sellerEntity.getAddress())
                .bizregNo(sellerEntity.getBizregNo())
                .ownerName(sellerEntity.getOwnerName())
                .bizPhoneNumber(sellerEntity.getBizPhoneNumber())
                .companyDescription(sellerEntity.getCompanyDescription())
                .picName(sellerEntity.getPicName())
                .phoneNumber(sellerEntity.getPhoneNumber())
                .sellerId(sellerEntity.getSellerId())
                .password(sellerEntity.getPassword())
                .email(sellerEntity.getEmail())
                .sellerKeyword(sellerEntity.getSellerKeyword())
                .sellerStatus(sellerEntity.getSellerStatus())
                .build();
    }
}
