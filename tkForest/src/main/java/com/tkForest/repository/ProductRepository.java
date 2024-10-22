package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	Page<ProductEntity> findByBrandContains(String searchWord, PageRequest of);

	Page<ProductEntity> findByProductNoContains(String searchWord, PageRequest of);

	Page<ProductEntity> findByProductDescriptionContains(String searchWord, PageRequest of);

	List<ProductEntity> findAllBySellerEntityOrderByProductNoDesc(Optional<SellerEntity> sellerEntity);
	
	// ProductNo로 
	Optional<ProductEntity> findByProductNo(Integer productNo);

}
