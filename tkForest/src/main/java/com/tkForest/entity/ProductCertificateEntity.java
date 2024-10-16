package com.tkForest.entity;

import com.tkForest.dto.ProductCertificateDTO;

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
@Table(name="PRODUCTCERTIFICATE")
public class ProductCertificateEntity {
	@Id
	@Column(name="PRODUCTNO")
	private Integer productNo;
	
	@Column(name="CERTIFICATETYPECODE")
	private Integer certificateTypeCode;
	
	public static ProductCertificateEntity toEntity(ProductCertificateDTO productCertificateDTO) {
		return ProductCertificateEntity.builder()
				.productNo(productCertificateDTO.getProductNo())
				.certificateTypeCode(productCertificateDTO.getCertificateTypeCode())
				.build();
	}
}