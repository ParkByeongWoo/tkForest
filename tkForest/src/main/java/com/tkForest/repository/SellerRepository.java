package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tkForest.entity.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, String> {

    // id로 셀러를 조회하는 메서드 정의
    Optional<SellerEntity> findBySellerId(String id);


    @Query("SELECT MAX(s.sellerMemberNo) FROM SellerEntity s")
	String findLastMemberNo();
}
