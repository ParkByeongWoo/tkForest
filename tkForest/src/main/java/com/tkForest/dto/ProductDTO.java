package com.tkForest.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
@Getter
@Setter 
@ToString
@Builder
public class ProductDTO { 

    // ProductDTO의 필드들 (ProductEntity와 동일한 필드)
    private Integer productNo;
    private String sellerMemberNo; 
    private LocalDate registrationDate;  // 자동으로 생성되지만 필요함
    private String productName;
    private String brand;
    private MultipartFile uploadFile;
    private String productImagePath1;
    private String productImagePath2;
    private String productDescription;
    private String keyword;
    private Integer viewCnt;

    private String companyName;
    private List<String> categoryNames;
    private List<Integer> productCertificateTypeCodes;
    
    public ProductDTO(
          Integer productNo
          , String sellerMemberNo
          , LocalDate registrationDate
          , String productName
          , String brand
          , String companyName) {
       this.productNo = productNo;
       this.sellerMemberNo = sellerMemberNo;
       this.registrationDate = registrationDate;
       this.productName = productName;
       this.brand = brand;
       this.companyName = companyName;
    }

    
    // Entity -> DTO 변환 메서드
    public static ProductDTO toDTO(ProductEntity productEntity, String sellerMemberNo) {
        return ProductDTO.builder()
                .productNo(productEntity.getProductNo())
                .sellerMemberNo(sellerMemberNo)  // SellerDTO로 변환 후 매핑
                .registrationDate(productEntity.getRegistrationDate())
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