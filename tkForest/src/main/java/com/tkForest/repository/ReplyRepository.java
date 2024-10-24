package com.tkForest.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tkForest.entity.InquiryEntity;
import com.tkForest.entity.ReplyEntity;

public interface ReplyRepository extends JpaRepository<ReplyEntity, Integer> {

	//List<ReplyEntity> findAllByInquiryEntityOrderByReplyNoDesc(Optional<InquiryEntity> inquiryEntity);
	List<ReplyEntity> findByInquiryEntity_InquiryNo(Integer inquiryNo);

	List<ReplyEntity> findAllByInquiryEntityOrderByReplyNoDesc(InquiryEntity inquiryEntity);
}
