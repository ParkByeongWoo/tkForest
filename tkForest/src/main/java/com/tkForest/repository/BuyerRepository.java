package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.BuyerEntity;

public interface BuyerRepository extends JpaRepository<BuyerEntity, String>{

	Optional<BuyerEntity> findByUsername(String username);
	
}
