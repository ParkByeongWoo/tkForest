
package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkForest.entity.CategoryEntity;
import com.tkForest.entity.PCategoryEntity;
import com.tkForest.entity.ProductEntity;

public interface PCategoryRepository extends JpaRepository<PCategoryEntity, Integer> {

	@Query("SELECT c.categoryEntity.categoryNo FROM PCategoryEntity c WHERE c.productEntity.productNo = :productNo")
	List<Integer> findCategoryNosByProductNo(@Param("productNo") Integer productNo);

	
	// PRODUCTNO에 해당하는 CATEGORYNO를 조회하는 쿼리
	//List<Integer> findByProductEntityProductNo(Integer productNo);
	//List<PCategoryEntity> findAllByCategoryEntityOrderByPCategoryNoDesc(Optional<CategoryEntity> categoryEntity);
	
	
}