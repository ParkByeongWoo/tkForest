package com.tkForest.entity;

import com.tkForest.dto.BCategoryDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name="B_CATEGORY")
public class BCategoryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="B_CATEGORYNO")
    private Integer bCategoryNo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "BUYER_MEMBERNO", referencedColumnName = "BUYER_MEMBERNO", nullable = false)
    private BuyerEntity buyerEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CATEGORYNO", referencedColumnName = "CATEGORYNO", nullable = false)
    private CategoryEntity categoryEntity;

    // DTO -> Entity 변환 메소드
    public static BCategoryEntity toEntity(BCategoryDTO bCategoryDTO, BuyerEntity buyerEntity, CategoryEntity categoryEntity) {
        return BCategoryEntity.builder()
                .bCategoryNo(bCategoryDTO.getBCategoryNo())
                .buyerEntity(buyerEntity)
                .categoryEntity(categoryEntity)
                .build();
    }

}
