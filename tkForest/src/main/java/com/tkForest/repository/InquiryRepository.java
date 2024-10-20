package com.tkForest.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import com.tkForest.entity.InquiryEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
    // BuyerEntity 내부의 buyerMemberNo 필드를 기반으로 검색하는 쿼리 메서드
    Page<InquiryEntity> findByBuyerEntity_BuyerMemberNoContains(String buyerMemberNo, Pageable pageable); 
}


//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
//import org.springframework.data.jpa.repository.JpaRepository;
//import com.tkForest.entity.InquiryEntity;
//
//public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
//
//    // 기존 페이징 + 검색 기능
//    Page<InquiryEntity> findBySubjectContains(String searchWord, Pageable pageable);
//    Page<InquiryEntity> findByBuyerMemberNoContains(String searchWord, Pageable pageable);
//    Page<InquiryEntity> findByContentsContains(String searchWord, Pageable pageable);
//    Page<InquiryEntity> findByBuyerEntityBuyerMemberNoContains(String buyerMemberNo, Pageable pageable);
//    
////    // 해당 바이어 또는 셀러만 조회할 수 있도록 조건 추가한 메서드들
////    Page<InquiryEntity> findBySubjectContainsAndBuyerEntityBuyerMemberNoOrSellerEntitySellerMemberNo(
////        String searchWord, String buyerMemberNo, String sellerMemberNo, Pageable pageable);
////
////    Page<InquiryEntity> findByBuyerEntityBuyerMemberNoContainsAndBuyerEntityBuyerMemberNoOrSellerEntitySellerMemberNo(
////        String searchWord, String buyerMemberNo, String sellerMemberNo, Pageable pageable);
////
////    Page<InquiryEntity> findByContentsContainsAndBuyerEntityBuyerMemberNoOrSellerEntitySellerMemberNo(
////        String searchWord, String buyerMemberNo, String sellerMemberNo, Pageable pageable);
//}
