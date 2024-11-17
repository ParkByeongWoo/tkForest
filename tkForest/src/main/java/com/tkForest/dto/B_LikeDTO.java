package com.tkForest.dto;

import java.time.LocalDateTime;

import com.tkForest.entity.B_LikeEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
public class B_LikeDTO {

    private Integer likeNo;
    private String buyerMemberNo;
    private String sellerMemberNo;
    private Integer productNo;
    private LocalDateTime likeCreatedDate;
    private String likeUseYn;

    // Entity -> DTO 변환 메서드
    public static B_LikeDTO toDTO(B_LikeEntity b_LikeEntity, String buyerMemberNo, String sellerMemberNo, Integer productNo) {
        return B_LikeDTO.builder()
                .likeNo(b_LikeEntity.getLikeNo())
                .buyerMemberNo(buyerMemberNo)
                .sellerMemberNo(sellerMemberNo)
                .productNo(productNo)
                .likeCreatedDate(b_LikeEntity.getLikeCreatedDate())
                .likeUseYn(b_LikeEntity.getLikeUseYn())
                .build();
    }
}

