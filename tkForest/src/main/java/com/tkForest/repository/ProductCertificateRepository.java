package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.CertificateEntity;
import com.tkForest.entity.ProductCertificateEntity;
import com.tkForest.entity.ProductEntity;

public interface ProductCertificateRepository extends JpaRepository<ProductCertificateEntity, Integer> {
	List<ProductCertificateEntity> findAllByProductEntityOrderByProductCertificateNoDesc(Optional<ProductEntity> productEntity);
	List<ProductCertificateEntity> findAllByCertificateEntityOrderByProductCertificateNoDesc(Optional<CertificateEntity> certificateEntity);
}
