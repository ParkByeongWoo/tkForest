package com.tkForest.entity;

import com.tkForest.dto.ProductCertificateDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name="PRODUCTCERTIFICATE")
public class ProductCertificateEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="PRODUCTCERTIFICATENO")
	private Integer productCertificateNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCTNO", referencedColumnName = "PRODUCTNO", nullable = false)
	private ProductEntity productEntity;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CERTIFICATETYPECODE", referencedColumnName = "CERTIFICATETYPECODE", nullable = false)
	private CertificateEntity certificateEntity;
	
	public static ProductCertificateEntity toEntity(ProductCertificateDTO productCertificateDTO, ProductEntity productEntity, CertificateEntity certificateEntity) {
		return ProductCertificateEntity.builder()
				.productCertificateNo(productCertificateDTO.getProductCertificateNo())
				.productEntity(productEntity)
				.certificateEntity(certificateEntity)
				.build();
	}
}