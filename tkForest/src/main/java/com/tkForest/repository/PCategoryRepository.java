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
   

   // CATEGORYNO에 해당하는 PRODUCTNO를 조회하는 쿼리
   @Query("SELECT c.productEntity.productNo FROM PCategoryEntity c WHERE c.categoryEntity.categoryNo = :categoryNo")
   List<Integer> findProductNosByCategoryNo(@Param("categoryNo") Integer categoryNo);
   
   // CATEGORYNO로 시작하는 PRODUCTNO를 조회하는 쿼리 (MySQL에서 Integer 사용)
//   @Query("SELECT c.productEntity.productNo FROM PCategoryEntity c WHERE CAST(c.categoryEntity.categoryNo AS string) LIKE CONCAT(:categoryNo, '%')")
//   List<Integer> findProductNosByCategoryNoStartsWith(@Param("categoryNo") Integer categoryNo);
   
   // 위의 코드가 Integer => String 변환하는 과정에서 생긴 오류 해결(엄격히 String으로 관리)
   @Query("SELECT c.productEntity.productNo FROM PCategoryEntity c WHERE CAST(c.categoryEntity.categoryNo AS string) LIKE CONCAT(CAST(:categoryNo AS string), '%')")
   List<Integer> findProductNosByCategoryNoStartsWith(@Param("categoryNo") Integer categoryNo);

}  