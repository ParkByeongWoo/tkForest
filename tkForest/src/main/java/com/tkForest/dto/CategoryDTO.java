package com.tkForest.dto;

import com.tkForest.entity.CategoryEntity;

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
public class CategoryDTO {
	private Integer categoryNo;
	private String categoryName;
	private Integer categoryDepth;
	private Integer categoryNo1;
	private Integer categoryNo2;
	private Integer categoryNo3;
	
    public static CategoryDTO toDTO(CategoryEntity categoryEntity) {
        return CategoryDTO.builder()
                .categoryNo(categoryEntity.getCategoryNo())
                .categoryName(categoryEntity.getCategoryName())
                .categoryDepth(categoryEntity.getCategoryDepth())
                .categoryNo1(categoryEntity.getCategoryNo1())
                .categoryNo2(categoryEntity.getCategoryNo2())
                .categoryNo3(categoryEntity.getCategoryNo3())
                .build();
    }
}
