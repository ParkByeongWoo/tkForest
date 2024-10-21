package com.tkForest.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.InquiryEntity;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {

    // 셀러의 sellerMemberNo로 인콰이어리 조회
    List<InquiryEntity> findBySellerEntity_SellerMemberNo(String sellerMemberNo);
    
    // 바이어의 buyerMemberNo로 인콰이어리 조회
    List<InquiryEntity> findByBuyerEntity_BuyerMemberNo(String buyerMemberNo);
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
