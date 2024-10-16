package com.tkForest.entity;

import com.tkForest.dto.PCategoryDTO;

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
@Table(name = "P_CATEGORY")
public class PCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "P_CATEGORYNO")
    private Integer pCategoryNo; // Primary Key

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRODUCTNO", referencedColumnName = "PRODUCTNO", nullable = false)
    private ProductEntity productEntity; // 외래 키로 ProductEntity 참조

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORYNO", referencedColumnName = "CATEGORYNO", nullable = false)
    private CategoryEntity categoryEntity; // 외래 키로 CategoryEntity 참조

    public static PCategoryEntity toEntity(PCategoryDTO pCategoryDTO, ProductEntity productEntity, CategoryEntity categoryEntity) {
        return PCategoryEntity.builder()
                .pCategoryNo(pCategoryDTO.getPCategoryNo())
                .productEntity(productEntity) // 외래 키로 ProductEntity 매핑
                .categoryEntity(categoryEntity) // 외래 키로 CategoryEntity 매핑
                .build();
    }

}