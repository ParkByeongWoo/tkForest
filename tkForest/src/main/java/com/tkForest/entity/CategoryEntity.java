package com.tkForest.entity;

import com.tkForest.dto.CategoryDTO;

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
@Table(name="CATEGORY")
public class CategoryEntity {
	@Id
	@Column(name="CATEGORYNO")
	private Integer categoryNo;
	
	@Column(name="CATEGORYNAME")
	private String categoryName;
	
	@Column(name="CATEGORYDEPTH")
	private Integer categoryDepth;
	
	@Column(name="CATEGORYNO1")
	private Integer categoryNo1;
	
	@Column(name="CATEGORYNO2")
	private Integer categoryNo2;
	
	@Column(name="CATEGORYNO3")
	private Integer categoryNo3;
	
    public static CategoryEntity toEntity(CategoryDTO categoryDTO) {
        return CategoryEntity.builder() // Builder 패턴 사용
                .categoryNo(categoryDTO.getCategoryNo())
                .categoryName(categoryDTO.getCategoryName())
                .categoryDepth(categoryDTO.getCategoryDepth())
                .categoryNo1(categoryDTO.getCategoryNo1())
                .categoryNo2(categoryDTO.getCategoryNo2())
                .categoryNo3(categoryDTO.getCategoryNo3())
                .build();
    }
	
}
