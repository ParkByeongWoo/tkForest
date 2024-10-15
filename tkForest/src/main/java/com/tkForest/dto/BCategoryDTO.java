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
	private Integer buyerMemberNo;
	private Integer categoryNo;
	
	public static BCategoryDTO toDTO(BCategoryEntity bCategoryEntity) {
		return BCategoryDTO.builder()
				.buyerMemberNo(bCategoryEntity.getBuyerMemberNo())
				.categoryNo(bCategoryEntity.getCategoryNo())
				.build();
	}
}
