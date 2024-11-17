package com.tkForest.entity;

import com.tkForest.dto.SellerCertificateDTO;

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
@Table(name="SELLERCERTIFICATE")
public class SellerCertificateEntity {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="SELLERCERTIFICATENO")
	private Integer sellerCertificateNo;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="SELLER_MEMBERNO", referencedColumnName = "SELLER_MEMBERNO", nullable = false)
	private SellerEntity sellerEntity;
	
    @ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CERTIFICATETYPECODE", referencedColumnName = "CERTIFICATETYPECODE", nullable = false)
	private CertificateEntity certificateEntity;
	
	public static SellerCertificateEntity toEntity(SellerCertificateDTO sellerCertificateDTO, SellerEntity sellerEntity, CertificateEntity certificateEntity) {
		return SellerCertificateEntity.builder()
				.sellerCertificateNo(sellerCertificateDTO.getSellerCertificateNo())
				.sellerEntity(sellerEntity)
				.certificateEntity(certificateEntity)
				.build();
	}
}