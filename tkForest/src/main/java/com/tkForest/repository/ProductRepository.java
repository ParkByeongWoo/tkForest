package com.tkForest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Integer>{

	Page<ProductEntity> findBySubjectContains(String searchWord, PageRequest of);

	Page<ProductEntity> findBybuyerMemberNoContains(String searchWord, PageRequest of);

	Page<ProductEntity> findBycontentsContains(String searchWord, PageRequest of);

}