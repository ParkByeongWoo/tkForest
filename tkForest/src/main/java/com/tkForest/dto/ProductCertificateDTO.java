package com.tkForest.dto;


import com.tkForest.entity.ProductCertificateEntity;

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
public class ProductCertificateDTO {
	private Integer productNo;
	private Integer certificateTypeCode;
	
	public static ProductCertificateDTO toDTO(ProductCertificateEntity productCertificateEntity) {
		return ProductCertificateDTO.builder()
				.productNo(productCertificateEntity.getProductNo())
				.certificateTypeCode(productCertificateEntity.getCertificateTypeCode())
				.build();
	}
}
