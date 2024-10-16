package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.SellerDTO;

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
@Table(name="SELLER")
public class SellerEntity {

    @Id
    @Column(name = "SELLER_MEMBERNO")
    private String sellerMemberNo;

    @Column(name = "SELLER_MEMBER_JOINDATE", nullable = false)
    @CreationTimestamp
    private LocalDateTime sellerMemberJoinDate;

    @Column(name = "COMPANYNAME", nullable = false)
    private String companyName;

    @Column(name = "ADDRESS")
    private String address;

    @Column(name = "BIZREGNO", nullable = false, unique = true)
    private String bizregNo;

    @Column(name = "OWNERNAME")
    private String ownerName;

    @Column(name = "BIZPHONENUMBER")
    private String bizPhoneNumber;

    @Column(name = "COMPANY_DESCRIPTION")
    private String companyDescription;

    @Column(name = "PICNAME", nullable = false)
    private String picName;

    @Column(name = "PHONENUMBER")
    private String phoneNumber;

    @Column(name = "ID", nullable = false, unique = true)
    private String id;

    @Column(name = "PWD", nullable = false)
    private String password;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "SELLER_KEYWORD", nullable = false)
    private String sellerKeyword;

    @Column(name = "SELLER_STATUS", nullable = false)
    private Boolean sellerStatus;

    // DTO -> Entity
    public static SellerEntity toEntity(SellerDTO sellerDTO) {
        SellerEntity entity = new SellerEntity();
        entity.sellerMemberNo = sellerDTO.getSellerMemberNo();
        entity.sellerMemberJoinDate = sellerDTO.getSellerMemberJoinDate();
        entity.companyName = sellerDTO.getCompanyName();
        entity.address = sellerDTO.getAddress();
        entity.bizregNo = sellerDTO.getBizregNo();
        entity.ownerName = sellerDTO.getOwnerName();
        entity.bizPhoneNumber = sellerDTO.getBizPhoneNumber();
        entity.companyDescription = sellerDTO.getCompanyDescription();
        entity.picName = sellerDTO.getPicName();
        entity.phoneNumber = sellerDTO.getPhoneNumber();
        entity.id = sellerDTO.getId();
        entity.password = sellerDTO.getPassword();
        entity.email = sellerDTO.getEmail();
        entity.sellerKeyword = sellerDTO.getSellerKeyword();
        entity.sellerStatus = sellerDTO.getSellerStatus();
        return entity;
    }
}
