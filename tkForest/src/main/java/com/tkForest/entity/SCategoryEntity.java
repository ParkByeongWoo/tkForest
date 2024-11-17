package com.tkForest.entity;

import com.tkForest.dto.SCategoryDTO;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "S_CATEGORY")
public class SCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "S_CATEGORYNO")
    private Integer sCategoryNo; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "SELLER_MEMBERNO", referencedColumnName = "SELLER_MEMBERNO", nullable = false)
    private SellerEntity sellerEntity; // 외래 키로 SellerEntity 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORYNO", referencedColumnName = "CATEGORYNO", nullable = false)
    private CategoryEntity categoryEntity; // 외래 키로 CategoryEntity 참조

    // DTO -> Entity 변환 메소드
    public static SCategoryEntity toEntity(SCategoryDTO sCategoryDTO, SellerEntity sellerEntity, CategoryEntity categoryEntity) {
        return SCategoryEntity.builder()
                .sCategoryNo(sCategoryDTO.getSCategoryNo())
                .sellerEntity(sellerEntity) // 외래 키로 SellerEntity 매핑
                .categoryEntity(categoryEntity) // 외래 키로 CategoryEntity 매핑
                .build();
    }
}
