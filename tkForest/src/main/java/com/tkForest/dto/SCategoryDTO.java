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
    private String sellerMemberNo;     // 셀러 DTO
    private Integer categoryNo;  // 카테고리 DTO

    // Entity -> DTO 변환 메소드
    public static SCategoryDTO toDTO(SCategoryEntity sCategoryEntity, String sellerMemberNo, Integer categoryNo) {
        return SCategoryDTO.builder()
                .sCategoryNo(sCategoryEntity.getSCategoryNo())
                .sellerMemberNo(sellerMemberNo)          // SellerDTO 설정
                .categoryNo(categoryNo)      // CategoryDTO 설정
                .build();
    }
}
