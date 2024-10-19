package com.tkForest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.CategoryEntity;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Integer> {

}
