
package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkForest.entity.B_LikeEntity;

public interface BLikeRepository extends JpaRepository<B_LikeEntity, Integer> {

//	// BuyerMemberNo에 해당하는 B_Like를 조회하는 쿼리
//	List<Integer> findByBuyerEntity_BuyerMemberNo(String buyerMemberNo);
//	
//	// buyerMemberNo로 좋아요한 상품들의 productNo 리스트를 가져오는 메소드
//    List<Integer> findByFromBuyerMemberNoLikeAndLikeUseYn(String buyerMemberNo, String likeUseYn);
//    
//    // 특정 buyerMemberNo와 likeUseYn이 'Y'인 B_LikeEntity 조회
//    List<B_LikeEntity> findByLikefromBuyerEntity_BuyerMemberNoAndLikeUseYn(String buyerMemberNo, String likeUseYn);

	// buyerMemberNo로 좋아요한 상품들의 productNo 리스트를 가져오는 메소드
//    List<Integer> findByLikefromBuyerEntity_BuyerMemberNoAndLikeUseYn(String buyerMemberNo, String likeUseYn);
    
    // 특정 buyerMemberNo와 likeUseYn이 'Y'인 B_LikeEntity 조회
    // List<B_LikeEntity> findByLikefromBuyerEntity_BuyerMemberNoAndLikeUseYn(String buyerMemberNo, String likeUseYn);

	// !!! 되는 코드 !!!
// // 특정 buyerMemberNo와 likeUseYn이 'Y'인 B_LikeEntity에서 productNo 값만 가져오는 쿼리
//    @Query("SELECT b.likedProductEntity.productNo FROM B_LikeEntity b WHERE b.likefromBuyerEntity.buyerMemberNo = 'B2' AND b.likeUseYn = 'Y'")
//    List<Integer> findProductNosByBuyerMemberNoAndLikeUseYn();
    
	// 되는 코드
    @Query("SELECT b.likedProductEntity.productNo FROM B_LikeEntity b WHERE b.likefromBuyerEntity.buyerMemberNo = :buyerMemberNo AND b.likeUseYn = :likeUseYn")
    List<Integer> findLikedProductsByBuyerMemberNo(@Param("buyerMemberNo") String buyerMemberNo, @Param("likeUseYn") String likeUseYn);

    
}
    
