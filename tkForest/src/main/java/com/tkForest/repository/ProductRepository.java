package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {

	// 전체 검색
	Page<ProductEntity> findByProductNoContains(String productNo, PageRequest of);

    // 상품명으로 검색
    Page<ProductEntity> findByProductNameContains(String productName, PageRequest of);

    // 브랜드로 검색
    Page<ProductEntity> findByBrandContains(String brand, PageRequest of);

	List<ProductEntity> findAllBySellerEntityOrderByProductNoDesc(Optional<SellerEntity> sellerEntity);
	
	// ProductNo로 
	Optional<ProductEntity> findByProductNo(Integer productNo);

	
	
    // 검색 (상품명 또는 브랜드) - 동적 정렬 포함
    Page<ProductEntity> findByProductNameContainsOrBrandContains(
            String productName, String brand, Pageable pageable);
}