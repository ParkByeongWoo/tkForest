package com.tkForest.dto;

import java.time.LocalDateTime;

import org.hibernate.Hibernate;
import org.springframework.web.multipart.MultipartFile;

import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Slf4j
public class ProductDTO {

    // ProductDTO의 필드들 (ProductEntity와 동일한 필드)
    private Integer productNo;
    private String sellerMemberNo; 
    private LocalDateTime registrationDate;  // 자동으로 생성되지만 필요함
    private String productName;
    private String brand;
	private MultipartFile uploadFile;
    private String productImagePath1;
    private String productImagePath2;
    private String productDescription;
    private String keyword;
    private Integer viewCnt;
    private String companyName;  // 작성자(셀러)의 회사명 도저언~~!

    public ProductDTO(
    		Integer productNo
    		, String sellerMemberNo
    		, LocalDateTime registrationDate
    		, String productName
    		, String brand
    		, String productImagePath1) {
    	this.productNo = productNo;
    	this.sellerMemberNo = sellerMemberNo;
    	this.registrationDate = registrationDate;
    	this.productName = productName;
    	this.brand = brand;
    	this.productImagePath1 = productImagePath1;
    }
    
    // Entity -> DTO 변환 메서드
    public static ProductDTO toDTO(ProductEntity productEntity) {
    	

        // Lazy 로딩 상태일 때 명시적으로 접근
    	SellerEntity seller = productEntity.getSellerEntity();
    	if (seller != null) {
    	    Hibernate.initialize(seller);
    	    log.info("Seller companyName: {}", seller.getCompanyName());
    	} else {
    	    log.warn("SellerEntity is null 따흐흑");
    	}
    	
        return ProductDTO.builder()
                .productNo(productEntity.getProductNo())
                .sellerMemberNo(seller.getSellerMemberNo())  // 초기화된 seller 객체에서 가져옴  // SellerDTO로 변환 후 매핑
                .companyName(seller.getCompanyName())  // SellerEntity에서 companyName 가져오기!!말고 // 초기화된 seller 객체에서 가져옴
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
