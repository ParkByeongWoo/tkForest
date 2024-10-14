package com.tkForest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, String> {

}
