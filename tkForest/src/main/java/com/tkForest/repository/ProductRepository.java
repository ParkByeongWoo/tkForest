package com.tkForest.repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{
	
	// 전체 검색
	Page<ProductEntity> findByProductNoContains(String productNo, PageRequest of);

    // 상품명으로 검색
    Page<ProductEntity> findByProductNameContains(String productName, PageRequest of);

    // 브랜드로 검색
    Page<ProductEntity> findByBrandContains(String brand, PageRequest of);

	Page<ProductEntity> findByProductNameContainsOrBrandContains(String productName, String brand, PageRequest of);

	List<ProductEntity> findAllBySellerEntityOrderByProductNoDesc(Optional<SellerEntity> sellerEntity);
	
	// ProductNo로 
	Optional<ProductEntity> findByProductNo(Integer productNo);

}

