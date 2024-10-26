
package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkForest.entity.SCategoryEntity;

public interface SCategoryRepository extends JpaRepository<SCategoryEntity, Integer> {

		// SellerNo에 해당하는 CATEGORYNO를 조회하는 쿼리
		// List<Integer> findBySellerMemberNo(String sellerMemberNo);
		
	    // List<SCategoryEntity> findBySellerEntity_SellerMemberNo(String sellerMemberNo);

	    
	    // Seller의 MemberNo로 SCategory 리스트 찾기
//	    @Query("SELECT s FROM SCategoryEntity s WHERE s.sellerEntity.sellerMemberNo = :sellerMemberNo")
//	    List<Integer> findBySellerMemberNo(@Param("sellerMemberNo") String sellerMemberNo);
	    
		// (Product에서 복사해온 것)
		// List<PCategoryEntity> findAllByCategoryEntityOrderByPCategoryNoDesc(Optional<CategoryEntity> categoryEntity);
	
	   // Seller의 MemberNo로 여러 CategoryNo를 찾기
//		   @Query("SELECT s.categoryEntity.categoryNo FROM SCategoryEntity s WHERE s.sellerEntity.sellerMemberNo = :sellerMemberNo")
//		    List<Integer> findCategoryNosBySellerMemberNo(@Param("sellerMemberNo") String sellerMemberNo);
	   
		// Seller의 MemberNo로 카테고리 번호 리스트 찾기
	    @Query("SELECT s.categoryEntity.categoryNo FROM SCategoryEntity s WHERE s.sellerEntity.sellerMemberNo = :sellerMemberNo")
	    List<Integer> findCategoryNosBySellerMemberNo(@Param("sellerMemberNo") String sellerMemberNo);


		   
	    
}