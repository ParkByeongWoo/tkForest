package com.tkForest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.CertificateEntity;

public interface CertificateRepository extends JpaRepository<CertificateEntity, Integer> {

}
