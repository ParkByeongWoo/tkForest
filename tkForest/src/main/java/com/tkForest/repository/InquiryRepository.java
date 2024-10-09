package com.tkForest.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.InquiryEntity;


public interface InquiryRepository extends JpaRepository<InquiryEntity, Integer> {
	// 3) 페이징 + 검색 기능
	Page<InquiryEntity> findBySubjectContains(String searchWord, PageRequest of);
	Page<InquiryEntity> findBybuyerMemberNoContains(String searchWord, PageRequest of);
	Page<InquiryEntity> findBycontentsContains(String searchWord, PageRequest of);
	
	
	// 2) 검색기능을 위해 추가
	/*
	List<InquiryEntity> findByInquiryTitleContains(String searchWord, Sort sort);
	List<InquiryEntity> findByInquiryWriterContains(String searchWord, Sort sort);
	List<InquiryEntity> findByInquiryContentContains(String searchWord, Sort sort);
	*/
}
