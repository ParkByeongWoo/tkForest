
package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.BCategoryEntity;
import com.tkForest.entity.SCategoryEntity;

public interface SCategoryRepository extends JpaRepository<SCategoryEntity, Integer> {

	// SellerNo에 해당하는 CATEGORYNO를 조회하는 쿼리
		// List<Integer> findBySellerMemberNo(Integer sellerMemberNo);
		
	    List<SCategoryEntity> findBySellerEntity_SellerMemberNo(String sellerMemberNo);

		// (Product에서 복사해온 것)
		// List<PCategoryEntity> findAllByCategoryEntityOrderByPCategoryNoDesc(Optional<CategoryEntity> categoryEntity);
	
}