package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tkForest.entity.BuyerEntity;

public interface BuyerRepository extends JpaRepository<BuyerEntity, String> {

    Optional<BuyerEntity> findById(String id);
    
 // buyerMemberNo로 BuyerEntity를 찾는 메서드 추가
    Optional<BuyerEntity> findByBuyerMemberNo(String buyerMemberNo);
    
}
