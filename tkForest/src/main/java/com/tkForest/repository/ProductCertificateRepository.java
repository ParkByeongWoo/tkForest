package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tkForest.entity.ProductCertificateEntity;

public interface ProductCertificateRepository extends JpaRepository<ProductCertificateEntity, Integer> {

   
   @Query("SELECT p.certificateEntity.certificateTypeCode FROM ProductCertificateEntity p WHERE p.productEntity.productNo = :productNo")
   List<Integer> findCertificateTypeCodesByProductNo(@Param("productNo") Integer productNo);
   
   //List<Integer> findByProductEntityProductNo(Integer productNo);
//   List<ProductCertificateEntity> findAllByCertificateEntityOrderByProductCertificateNoDesc(Optional<CertificateEntity> certificateEntity);

}
 