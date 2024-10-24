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

   // sellerMemberNo로 상품 조회
   List<ProductEntity> findBySellerEntitySellerMemberNo(String sellerMemberNo);
   
   // ProductNo로 
   Optional<ProductEntity> findByProductNo(Integer productNo);

   // productNo 목록을 기준으로 해당하는 ProductEntity 리스트를 가져오는 메소드
   List<ProductEntity> findByProductNoIn(List<Integer> productNos);
   Page<ProductEntity> findPageByProductNoIn(List<Integer> productNos, PageRequest pageRequest);

   // 상품 번호로 상품 조회
   Optional<ProductEntity> findById(Integer productNo);
   
   
}


