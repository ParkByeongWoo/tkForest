package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.tkForest.entity.B_LikeEntity;

@Repository
public interface B_LikeRepository extends JpaRepository<B_LikeEntity, Integer> {

    // 좋아요가 유효하고, 특정 상품에 대한 좋아요만 필터링하는 쿼리
    @Query("SELECT b.likedProductEntity.productNo FROM B_LikeEntity b WHERE b.likedProductEntity IS NOT NULL AND b.likeUseYn = 'Y'")
    List<Integer> findLikedProductNos();
}
