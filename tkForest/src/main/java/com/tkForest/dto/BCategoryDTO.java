package com.tkForest.dto;

import com.tkForest.entity.BCategoryEntity;

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
public class BCategoryDTO {
	private Integer bCategoryNo;
	private BuyerDTO buyer;	
	private CategoryDTO category;	
	
	public static BCategoryDTO toDTO(BCategoryEntity bCategoryEntity, BuyerDTO buyerDTO, CategoryDTO categoryDTO) {
		return BCategoryDTO.builder()
				.bCategoryNo(bCategoryEntity.getBCategoryNo())
				.buyer(buyerDTO)
				.category(categoryDTO)
				.build();
	}
}
