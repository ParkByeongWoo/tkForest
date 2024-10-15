package com.tkForest.entity;

import com.tkForest.dto.BCategoryDTO;

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
@Table(name="B_CATEGORY")
public class BCategoryEntity {
	@Id
	@Column(name="BUYER_MAMBERNO")
	private Integer buyerMemberNo;
	
	@Column(name="CATEGORYNO")
	private Integer categoryNo;
	
	
	public static BCategoryEntity toEntity(BCategoryDTO bCategoryDTO) {
		return BCategoryEntity.builder()
				.buyerMemberNo(bCategoryDTO.getBuyerMemberNo())
				.categoryNo(bCategoryDTO.getCategoryNo())
				.build();
	}
}
