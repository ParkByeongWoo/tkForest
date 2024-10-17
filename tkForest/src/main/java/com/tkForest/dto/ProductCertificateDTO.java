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
	private Integer productCertificateNo;
	private ProductDTO product;
	private CertificateDTO certificate;
	
	public static ProductCertificateDTO toDTO(ProductCertificateEntity productCertificateEntity, ProductDTO productDTO, CertificateDTO certificateDTO) {
		return ProductCertificateDTO.builder()
				.productCertificateNo(productCertificateEntity.getProductCertificateNo())
				.product(productDTO)
				.certificate(certificateDTO)
				.build();
	}
}
