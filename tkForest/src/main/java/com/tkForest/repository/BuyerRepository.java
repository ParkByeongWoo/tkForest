package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.tkForest.entity.BuyerEntity;

public interface BuyerRepository extends JpaRepository<BuyerEntity, String> {

    Optional<BuyerEntity> findByBuyerId(String id);

    
    @Query("SELECT MAX(b.buyerMemberNo) FROM BuyerEntity b")
	String findLastMemberNo();
}
