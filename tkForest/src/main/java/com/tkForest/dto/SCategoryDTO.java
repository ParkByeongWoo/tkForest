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
    private Integer sCategoryNo; // Primary Key
    private SellerDTO seller;     // 셀러 DTO
    private CategoryDTO category;  // 카테고리 DTO

    // Entity -> DTO 변환 메소드
    public static SCategoryDTO toDTO(SCategoryEntity sCategoryEntity, SellerDTO sellerDTO, CategoryDTO categoryDTO) {
        return SCategoryDTO.builder()
                .sCategoryNo(sCategoryEntity.getSCategoryNo())
                .seller(sellerDTO)          // SellerDTO 설정
                .category(categoryDTO)      // CategoryDTO 설정
                .build();
    }
}
