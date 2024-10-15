package com.tkForest.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.tkForest.dto.B_LikeDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
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
@Table(name = "B_LIKE")
public class B_LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LIKENO")
    private Integer likeNo;

    @Column(name = "FROM_BUYER_MEMBERNO_LIKE", length = 10, nullable = false)
    private String fromBuyerMemberNoLike;

    @Column(name = "TO_SELLER_MEMBERNO_LIKE", length = 10)
    private String toSellerMemberNoLike;

    @Column(name = "TO_PRODUCTNO_LIKE")
    private Integer toProductNoLike;

    @Column(name = "LIKE_CREATEDDATE", nullable = false)
    @CreationTimestamp  // 자동으로 현재 시간을 설정
    private LocalDateTime likeCreatedDate;

    @Column(name = "LIKE_USEYN", nullable = false, length = 1)
    private String likeUseYn;

    // DTO -> Entity 변환 메서드
    public static B_LikeEntity toEntity(B_LikeDTO b_LikeDTO) {
        return B_LikeEntity.builder()
                .likeNo(b_LikeDTO.getLikeNo())
                .fromBuyerMemberNoLike(b_LikeDTO.getFromBuyerMemberNoLike())
                .toSellerMemberNoLike(b_LikeDTO.getToSellerMemberNoLike())
                .toProductNoLike(b_LikeDTO.getToProductNoLike())
                .likeCreatedDate(b_LikeDTO.getLikeCreatedDate())
                .likeUseYn(b_LikeDTO.getLikeUseYn())
                .build();
    }
}

