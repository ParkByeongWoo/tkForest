package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkForest.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

	// Categoryname으로 CatogoryNo 찾기
	Optional<CategoryEntity> findByCategoryName(String categoryname);
	
	// CategoryNo로 여러 CategoryName 찾기
	@Query("SELECT c.categoryName FROM CategoryEntity c WHERE c.categoryNo IN :categoryNo")
	List<String> findCategoryNameByCategoryNo(@Param("categoryNo") List<Integer> categoryNo);

	
}
