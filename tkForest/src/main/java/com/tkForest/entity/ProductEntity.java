package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.ProductDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
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
@Table(name="product")
public class ProductEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PRODUCTNO")
	private Long productNo;
	
	// product : category의 관계 => 1 : 多
	// categoryNo : Join컬럼
	// @ManyToMany(fetch=FetchType.LAZY)	// One:상품(나), Many:카테고리
	
	@JoinColumn(name="CATEGORYNO")
	private int categoryNo;
	
	@Column(name="SELLER_MEMBERNO")
	private int sellerMemberNo;
	
	@Column(name="REGISTRATIONDATE")
	@CreationTimestamp 					// 상품 등록될 때 자동으로 날짜 세팅
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
	private int viewCnt;

	// DTO -> Entity
	public static ProductEntity toEntity(ProductDTO productDTO) {
		return ProductEntity.builder()
				.productNo(productDTO.getProductNo())
				.categoryNo(productDTO.getCategoryNo())
				.sellerMemberNo(productDTO.getSellerMemberNo())
				.registrationDate(productDTO.getRegistrationDate())	// 등록일은 자동생성
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
