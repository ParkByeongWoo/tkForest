
package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.SCategoryEntity;

import com.tkForest.entity.SellerCertificateEntity;

public interface SellerCertificateRepository extends JpaRepository<SellerCertificateEntity, Integer> {

}