package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
@Repository
public interface ProductRepository extends JpaRepository<ProductEntity, Integer> {


    // 전체 검색 - productNo 기반으로 검색 (필요한 경우)
    Page<ProductEntity> findByProductNoContains(String productNo, PageRequest pageRequest);
    
    // 상품명으로 검색
    Page<ProductEntity> findByProductNameContains(String productName, PageRequest pageRequest);

    // 브랜드로 검색

    Page<ProductEntity> findByBrandContains(String brand, PageRequest pageRequest);

    // sellerMemberNo로 상품 조회
    List<ProductEntity> findBySellerEntitySellerMemberNo(String sellerMemberNo);
   
    // ProductNo로 단일 상품 조회
    Optional<ProductEntity> findByProductNo(Integer productNo);


    // SellerEntity로 모든 상품 조회 (상품 번호 내림차순 정렬)
    List<ProductEntity> findAllBySellerEntityOrderByProductNoDesc(Optional<SellerEntity> sellerEntity);

    // 검색 (상품명 또는 브랜드) - 동적 정렬 포함
    Page<ProductEntity> findByProductNameContainsOrBrandContains(String productName, String brand, PageRequest pageRequest);

    // productNo 목록을 기준으로 해당하는 ProductEntity 리스트를 가져오는 메소드 (List로 반환)
    List<ProductEntity> findByProductNoIn(List<Integer> productNos);

    // 상품 번호로 상품 조회
    Optional<ProductEntity> findById(Integer productNo);

    // productNo 목록을 기준으로 해당하는 ProductEntity 리스트를 페이징 처리하여 가져오는 메소드
    Page<ProductEntity> findPageByProductNoIn(List<Integer> productNos, PageRequest pageRequest);

    // 추가: 상품명과 브랜드를 검색하면서 productNo 목록을 기준으로 필터링하는 메소드 (카테고리 필터링과 함께 사용 가능)
    Page<ProductEntity> findByProductNameContainsOrBrandContainsAndProductNoIn(
        String productName, String brand, List<Integer> productNos, PageRequest pageRequest);

    // 추가: 상품명 검색과 productNo 목록 필터링 동시 적용
    Page<ProductEntity> findByProductNameContainsAndProductNoIn(
        String productName, List<Integer> productNos, PageRequest pageRequest);

    // 추가: 브랜드 검색과 productNo 목록 필터링 동시 적용
    Page<ProductEntity> findByBrandContainsAndProductNoIn(
        String brand, List<Integer> productNos, PageRequest pageRequest);
    
    // 카테고리 필터링 오류나서 query 없이 단순 필터링은 적용되나 확인용
	Page<ProductEntity> findByProductNoIn(List<Integer> uniqueProductNos, PageRequest of);
	
}

