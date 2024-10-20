
package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.BCategoryEntity;
import com.tkForest.entity.SCategoryEntity;

public interface BCategoryRepository extends JpaRepository<BCategoryEntity, Integer> {

	// BuyerMemberNo에 해당하는 CATEGORYNO를 조회하는 쿼리
		// List<Integer> findByBuyerMemberNo(Integer buyerMemberNo);
		
	    List<BCategoryEntity> findByBuyerEntity_BuyerMemberNo(String buyerMemberNo);

		// (Product에서 복사해온 것)
		// List<PCategoryEntity> findAllByCategoryEntityOrderByPCategoryNoDesc(Optional<CategoryEntity> categoryEntity);
}