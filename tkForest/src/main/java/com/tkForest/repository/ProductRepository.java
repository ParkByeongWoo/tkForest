package com.tkForest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {

	// 레포지토리
}
