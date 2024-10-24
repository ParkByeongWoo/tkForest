package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.B_LikeEntity;

public interface B_LikeRepository extends JpaRepository<B_LikeEntity, Integer> {

	List<B_LikeEntity> findByLikefromBuyerEntity_BuyerMemberNoContains(String buyerMemberNo);
	
}
