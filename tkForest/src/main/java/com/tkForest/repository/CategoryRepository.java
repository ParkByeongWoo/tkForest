package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.CategoryEntity;


public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

	// Categoryname으로 CatogoryNo 찾기
	Optional<CategoryEntity> findByCategoryName(String categoryname);
}
