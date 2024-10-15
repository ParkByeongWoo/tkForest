package com.tkForest.entity;


import com.tkForest.dto.PCategoryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name="P_CATEGORY")
public class PCategoryEntity {
	@Id
	@Column(name="PRODUCTNO")
	private Integer productNo;
	
	@Column(name="CATEGORYNO")
	private Integer categoryNo;
	
	
	public static PCategoryEntity toEntity(PCategoryDTO pCategoryDTO) {
		return PCategoryEntity.builder()
				.productNo(pCategoryDTO.getProductNo())
				.categoryNo(pCategoryDTO.getCategoryNo())
				.build();
	}
}
