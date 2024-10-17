package com.tkForest.dto;

import com.tkForest.entity.SellerCertificateEntity;

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
public class SellerCertificateDTO {
	private Integer sellerCertificateNo;
	private SellerDTO seller;
	private CertificateDTO certificate;
	
	public static SellerCertificateDTO toDTO(SellerCertificateEntity sellerCertificateEntity, SellerDTO sellerDTO, CertificateDTO certificateDTO) {
		return SellerCertificateDTO.builder()
				.sellerCertificateNo(sellerCertificateEntity.getSellerCertificateNo())
				.seller(sellerDTO)
				.certificate(certificateDTO)
				.build();
	}
}
