
package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tkForest.entity.CategoryEntity;
import com.tkForest.entity.PCategoryEntity;
import com.tkForest.entity.ProductEntity;

public interface PCategoryRepository extends JpaRepository<PCategoryEntity, Integer> {

    List<PCategoryEntity> findAllByProductEntityOrderByPCategoryNoDesc(Optional<ProductEntity> productEntity);
    List<PCategoryEntity> findAllByCategoryEntityOrderByPCategoryNoDesc(Optional<CategoryEntity> categoryEntity);
}