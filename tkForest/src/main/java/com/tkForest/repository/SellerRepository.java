package com.tkForest.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.SellerEntity;

public interface SellerRepository extends JpaRepository<SellerEntity, String>{

	Optional<SellerEntity> findByUsername(String username);

}
