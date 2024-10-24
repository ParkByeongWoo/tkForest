package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.B_LikeDTO;

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
@Table(name = "B_LIKE")
public class B_LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKENO")
    private Integer likeNo;

    // From Buyer (외래키로 BuyerEntity 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FROM_BUYER_MEMBERNO_LIKE", referencedColumnName = "BUYER_MEMBERNO", nullable = false)
    private BuyerEntity likefromBuyerEntity; // 좋아요 누르는 바이어

    // To Seller (외래키로 SellerEntity 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_SELLER_MEMBERNO_LIKE", referencedColumnName = "SELLER_MEMBERNO")
    private SellerEntity likedSellerEntity; // 좋아요 받는 셀러

    // To Product (외래키로 ProductEntity 참조)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TO_PRODUCTNO_LIKE", referencedColumnName = "PRODUCTNO")
    private ProductEntity likedProductEntity; //  좋아요 받는 상품 

    @Column(name = "LIKE_CREATEDDATE", nullable = false)
    @CreationTimestamp  // 자동으로 현재 시간을 설정
    private LocalDateTime likeCreatedDate;

    @Column(name = "LIKE_USEYN", nullable = false, length = 1)
    private String likeUseYn;

    // DTO -> Entity 변환 메서드
    public static B_LikeEntity toEntity(B_LikeDTO b_LikeDTO, BuyerEntity likefromBuyerEntity, SellerEntity likedSellerEntity, ProductEntity likedProductEntity) {
        return B_LikeEntity.builder()
                .likeNo(b_LikeDTO.getLikeNo())
                .likefromBuyerEntity(likefromBuyerEntity)  // 좋아요 누른 바이어 매핑
                .likedSellerEntity(likedSellerEntity)      // 좋아요 받은 셀러 매핑
                .likedProductEntity(likedProductEntity)    // 좋아요 받은 상품 매핑
                .likeCreatedDate(b_LikeDTO.getLikeCreatedDate())
                .likeUseYn(b_LikeDTO.getLikeUseYn())
                .build();
    }
    

}

