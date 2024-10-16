package com.tkForest.dto;

import com.tkForest.entity.SCategoryEntity;

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
public class SCategoryDTO {
	private Integer sellerMemberNo;
	private Integer categoryNo;
	
	public static SCategoryDTO toDTO(SCategoryEntity sCategoryEntity) {
		return SCategoryDTO.builder()
				.sellerMemberNo(sCategoryEntity.getSellerMemberNo())
				.categoryNo(sCategoryEntity.getCategoryNo())
				.build();
	}
}