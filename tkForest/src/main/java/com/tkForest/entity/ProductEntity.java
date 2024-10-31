package com.tkForest.entity;

import java.time.LocalDate;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.tkForest.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

    @Id
    @Column(name="PRODUCTNO")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer productNo;

    // SellerEntity와 @ManyToOne 관계 설정
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_MEMBERNO", referencedColumnName = "SELLER_MEMBERNO", nullable=false)
    private SellerEntity sellerEntity;  // 외래키 관계로 SellerEntity 참조


    @Column(name="REGISTRATIONDATE")
    @CreationTimestamp  // 상품 등록될 때 자동으로 날짜 세팅
    private LocalDate registrationDate;

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

    
    // DTO -> Entity 변환 메서드
    public static ProductEntity toEntity(ProductDTO productDTO, SellerEntity sellerEntity) {
        return ProductEntity.builder()
                .productNo(productDTO.getProductNo())
                .sellerEntity(sellerEntity)  // 외래키로 SellerEntity 매핑
                .registrationDate(productDTO.getRegistrationDate())  // 등록일 자동 생성
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