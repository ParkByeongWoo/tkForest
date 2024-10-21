package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.SellerEntity;

public interface BuyerRepository extends JpaRepository<BuyerEntity, String> {
	
	// id로 바이어를 조회하는 메서드 정의
    Optional<BuyerEntity> findByBuyerId(String id);
    
    Optional<BuyerEntity> findByBuyerMemberNo(String buyerMemberNo);

    @Query("SELECT MAX(b.buyerMemberNo) FROM BuyerEntity b")
	String findLastMemberNo();
}
