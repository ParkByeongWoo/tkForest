package com.kdigital.spring7.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import com.kdigital.spring7.entity.BoardEntity;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
	// 3) 페이징 + 검색 기능
	Page<BoardEntity> findByBoardTitleContains(String searchWord, PageRequest of);
	Page<BoardEntity> findByBoardWriterContains(String searchWord, PageRequest of);
	Page<BoardEntity> findByBoardContentContains(String searchWord, PageRequest of);
	
	
	// 2) 검색기능을 위해 추가
	/*
	List<BoardEntity> findByBoardTitleContains(String searchWord, Sort sort);
	List<BoardEntity> findByBoardWriterContains(String searchWord, Sort sort);
	List<BoardEntity> findByBoardContentContains(String searchWord, Sort sort);
	*/
}
