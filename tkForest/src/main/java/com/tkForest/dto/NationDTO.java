package com.tkForest.dto;

import com.tkForest.entity.NationEntity;

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
public class NationDTO {
	private String nationCode;
	private String nationName;

    // Entity -> DTO 변환 메소드
    public static NationDTO toDTO(NationEntity nationEntity) {
        return NationDTO.builder()
                .nationCode(nationEntity.getNationCode())
                .nationName(nationEntity.getNationName())
                .build();
    }
}

