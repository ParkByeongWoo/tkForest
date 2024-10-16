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
    private Integer pCategoryNo; 
    private ProductDTO product;   
    private CategoryDTO category;  

    public static PCategoryDTO toDTO(PCategoryEntity pCategoryEntity, ProductDTO productDTO, CategoryDTO categoryDTO) {
        return PCategoryDTO.builder()
                .pCategoryNo(pCategoryEntity.getPCategoryNo())
                .product(productDTO)   
                .category(categoryDTO)  
                .build();
    }
}