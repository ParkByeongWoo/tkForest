package com.tkForest.dto;

import java.util.List;

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
	private String sellerMemberNo;
	private Integer certificateTypeCode;
	private List<Integer> sellerCertificateTypeCodes;
	

	public static SellerCertificateDTO toDTO(SellerCertificateEntity sellerCertificateEntity, String sellerMemberNo, Integer certificateTypeCode) {
		return SellerCertificateDTO.builder()
				.sellerCertificateNo(sellerCertificateEntity.getSellerCertificateNo())
				.sellerMemberNo(sellerMemberNo)
				.certificateTypeCode(certificateTypeCode)
				.build();

	}
}

