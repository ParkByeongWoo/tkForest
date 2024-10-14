package com.tkForest.dto;

import java.time.LocalDateTime;

import org.springframework.web.multipart.MultipartFile;

import com.tkForest.entity.ProductEntity;

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
public class ProductDTO {
   private int productNo;
   private String sellerMemberNo;
   private LocalDateTime registrationDate;
   private String productName;
   private String brand;
   private String productImagePath1;
   private String productImagePath2;
   private String productDescription;
   private String keyword;
   private int viewCnt;
   
   private MultipartFile uploadFile;

   public ProductDTO(int productNo
		   , String sellerMemeberNo
		   , LocalDateTime registrationDate
		   , String productName
		   , String brand
		   , String productImagePath1
		   , String productDescription
		   , String keyword
		   , int viewCnt) {
	   this.productNo = productNo;
	   this.sellerMemberNo = sellerMemeberNo;
	   this.registrationDate = registrationDate;
	   this.productName = productName;
	   this.brand = brand;
	   this.productImagePath1 = productImagePath1;
	   this.productDescription = productDescription;
	   this.keyword = keyword; 
	   this.viewCnt = viewCnt;
   }
   
   
   // DTO -> Entity
   public static ProductDTO toDTO(ProductEntity productEntity) {
      return ProductDTO.builder()
            .productNo(productEntity.getProductNo())
            .sellerMemberNo(productEntity.getSellerMemberNo())
            .registrationDate(productEntity.getRegistrationDate())   // 등록일은 자동생성
            .productName(productEntity.getProductName())
            .brand(productEntity.getBrand())
            .productImagePath1(productEntity.getProductImagePath1())
            .productImagePath2(productEntity.getProductImagePath2())
            .productDescription(productEntity.getProductDescription())
            .keyword(productEntity.getKeyword())
            .viewCnt(productEntity.getViewCnt())
            .build();
   }
}
