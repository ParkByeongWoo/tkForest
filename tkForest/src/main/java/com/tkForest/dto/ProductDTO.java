package com.tkForest.dto;

import java.time.LocalDateTime;

import com.tkForest.entity.ProductEntity;

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
public class ProductDTO {

    // ProductDTO의 필드들 (ProductEntity와 동일한 필드)
    private Long productNo;
    private int categoryNo;
    private int sellerMemberNo;
}