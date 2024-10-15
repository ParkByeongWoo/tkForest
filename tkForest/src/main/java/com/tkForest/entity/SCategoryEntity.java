package com.tkForest.entity;

import com.tkForest.dto.SCategoryDTO;

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
@Table(name="S_CATEGORY")
public class SCategoryEntity {
	@Id
	@Column(name="SELLER_MAMBERNO")
	private Integer sellerMemberNo;
	
	@Column(name="CATEGORYNO")
	private Integer categoryNo;
	
	
	public static SCategoryEntity toEntity(SCategoryDTO sCategoryDTO) {
		return SCategoryEntity.builder()
				.sellerMemberNo(sCategoryDTO.getSellerMemberNo())
				.categoryNo(sCategoryDTO.getCategoryNo())
				.build();
	}
}
