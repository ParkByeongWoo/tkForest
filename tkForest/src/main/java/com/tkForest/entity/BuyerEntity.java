package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.BuyerDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "BUYER")
public class BuyerEntity {

    @Id
    @Column(name = "BUYER_MEMBERNO")
    private String buyerMemberNo;

    @Column(name = "NATIONCODE", nullable = false)
    private String nationCode; 

    @Column(name = "BUYER_MEMBER_JOINDATE", nullable = false)
    @CreationTimestamp
    private LocalDateTime buyerMemberJoinDate;

    @Column(name = "COMPANYNAME")
    private String companyName;

    @Column(name = "BIZPHONENUMBER")
    private String bizPhoneNumber;

    @Column(name = "COMPANYDESCRIPTION")
    private String companyDescription;

    @Column(name = "PICNAME", nullable = false)
    private String picName;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Column(name = "ID", nullable = false, unique = true)
    private String buyerId;

    @Column(name = "PWD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "CONCERN_KEYWORD", nullable = false)
    private String concernKeyword;

    @Column(name = "BUYER_STATUS", nullable = false)
    private Boolean buyerStatus;

    // DTO -> Entity 변환 메소드
    public static BuyerEntity toEntity(BuyerDTO buyerDTO) {
        return BuyerEntity.builder()
                .buyerMemberNo(buyerDTO.getBuyerMemberNo())
                .nationCode(buyerDTO.getNationCode()) 
                .buyerMemberJoinDate(buyerDTO.getBuyerMemberJoinDate())
                .companyName(buyerDTO.getCompanyName())
                .bizPhoneNumber(buyerDTO.getBizPhoneNumber())
                .companyDescription(buyerDTO.getCompanyDescription())
                .picName(buyerDTO.getPicName())
                .phoneNumber(buyerDTO.getPhoneNumber())
                .buyerId(buyerDTO.getBuyerId())
                .password(buyerDTO.getPassword())
                .email(buyerDTO.getEmail())
                .concernKeyword(buyerDTO.getConcernKeyword())
                .buyerStatus(buyerDTO.getBuyerStatus())
                .build();
    }
}
