package com.tkForest.entity;

import com.tkForest.dto.SellerCertificateDTO;

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
@Table(name="SELLERTIFICATE")
public class SellerCertificateEntity {
	@Id
	@Column(name="SELLER_MEMBERNO")
	private Integer sellerMemberNo;
	
	@Column(name="CERTIFICATETYPECODE")
	private Integer certificateTypeCode;
	
	public static SellerCertificateEntity toEntity(SellerCertificateDTO sellerCertificateDTO) {
		return SellerCertificateEntity.builder()
				.sellerMemberNo(sellerCertificateDTO.getSellerMemberNo())
				.certificateTypeCode(sellerCertificateDTO.getCertificateTypeCode())
				.build();
	}
}
