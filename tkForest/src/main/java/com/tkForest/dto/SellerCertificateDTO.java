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
	private Integer sellerMemberNo;
	private Integer certificateTypeCode;
	
	public static SellerCertificateDTO toDTO(SellerCertificateEntity sellerCertificateEntity) {
		return SellerCertificateDTO.builder()
				.sellerMemberNo(sellerCertificateEntity.getSellerMemberNo())
				.certificateTypeCode(sellerCertificateEntity.getCertificateTypeCode())
				.build();
	}
}
