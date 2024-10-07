package com.kdigital.ajaxtest.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.ajaxtest.entity.GuestbookEntity;


public interface GuestbookRepository extends JpaRepository<GuestbookEntity, Long> {

	// 두 개 이상의 조건으로 삭제할 때 
	void deleteBySeqAndPwd(Long seq, String pwd);

}
