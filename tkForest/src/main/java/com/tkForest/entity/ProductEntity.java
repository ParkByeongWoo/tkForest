package com.tkForest.entity;

import java.time.LocalDateTime;


import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tkForest.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
@Table(name="PRODUCT")
@EntityListeners(AuditingEntityListener.class)
public class ProductEntity {

   
   // Entity
   @Id
   @GeneratedValue(strategy=GenerationType.IDENTITY)
   private Integer productNo;
   
   @Column(name="SELLER_MEMBERNO")
   private String sellerMemberNo;
   
   @Column(name="REGISTRATIONDATE")
   @CreationTimestamp                // 상품 등록될 때 자동으로 날짜 세팅
   private LocalDateTime registrationDate;
   
   @Column(name="PRODUCTNAME", nullable=false)
   private String productName;
   
   @Column(name="BRAND")
   private String brand;
   
   @Column(name="PRODUCT_IMAGE_PATH_1")
   private String productImagePath1;

   @Column(name="PRODUCT_IMAGE_PATH_2")
   private String productImagePath2;
   
   @Column(name="PRODUCT_DESCRIPTION")
   private String productDescription;
   
   @Column(name="KEYWORD")
   private String keyword;
   
   @Column(name="VIEWCNT")
   private Integer viewCnt;

   
   // DTO -> Entity
   public static ProductEntity toEntity(ProductDTO productDTO) {
      return ProductEntity.builder()
            .productNo(productDTO.getProductNo())
            .sellerMemberNo(productDTO.getSellerMemberNo())
            .registrationDate(productDTO.getRegistrationDate())   // 등록일은 자동생성
            .productName(productDTO.getProductName())
            .brand(productDTO.getBrand())
            .productImagePath1(productDTO.getProductImagePath1())
            .productImagePath2(productDTO.getProductImagePath2())
            .productDescription(productDTO.getProductDescription())
            .keyword(productDTO.getKeyword())
            .viewCnt(productDTO.getViewCnt())
            .build();
   }

   
}

