package com.tkForest.entity;

import com.tkForest.dto.CertificateDTO;

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
@Table(name="CERTIFICATE")
public class CertificateEntity {
	@Id
	@Column(name="CERTIFICATETYPECODE")
	private Integer certificateTypeCode;
		
	@Column(name="CERTIFICATENAME")
	private String certificateName;
	
    public static CertificateEntity toEntity(CertificateDTO certificateDTO) {
        return CertificateEntity.builder()
                .certificateTypeCode(certificateDTO.getCertificateTypeCode())
                .certificateName(certificateDTO.getCertificateName())
                .build();
    }
}
