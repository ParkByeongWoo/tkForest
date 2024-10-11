package com.tkForest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.InquiryEntity;


public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
	// 기존 페이징 + 검색 기능
	Page<InquiryEntity> findBySubjectContains(String searchWord, PageRequest of);
	Page<InquiryEntity> findBybuyerMemberNoContains(String searchWord, PageRequest of);
	Page<InquiryEntity> findBycontentsContains(String searchWord, PageRequest of);
	
//	 // 해당 바이어 또는 셀러만 조회할 수 있도록 조건 추가한 메서드들
//    Page<InquiryEntity> findBySubjectContainsAndBuyerMemberNoOrSellerMemberNo(
//        String searchWord, Integer buyerMemberNo, Integer sellerMemberNo, PageRequest of);
//    
//    Page<InquiryEntity> findByBuyerMemberNoContainsAndBuyerMemberNoOrSellerMemberNo(
//        String searchWord, Integer buyerMemberNo, Integer sellerMemberNo, PageRequest of);
//    
//    Page<InquiryEntity> findByContentsContainsAndBuyerMemberNoOrSellerMemberNo(
//        String searchWord, Integer buyerMemberNo, Integer sellerMemberNo, PageRequest of);
}