package com.tkForest.dto;


import com.tkForest.entity.PCategoryEntity;

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

public class PCategoryDTO {
	private Integer productNo;
	private Integer categoryNo;
	
	public static PCategoryDTO toDTO(PCategoryEntity pCategoryEntity) {
		return PCategoryDTO.builder()
				.productNo(pCategoryEntity.getProductNo())
				.categoryNo(pCategoryEntity.getCategoryNo())
				.build();
	}
}