package com.tkForest.dto;

import com.tkForest.entity.CertificateEntity;

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
public class CertificateDTO {
	private Integer certificateTypeCode;
	private String certificateName;
	
    public static CertificateDTO toDTO(CertificateEntity certificateEntity) {
        return CertificateDTO.builder()
                .certificateTypeCode(certificateEntity.getCertificateTypeCode())
                .certificateName(certificateEntity.getCertificateName())
                .build();
    }
}
