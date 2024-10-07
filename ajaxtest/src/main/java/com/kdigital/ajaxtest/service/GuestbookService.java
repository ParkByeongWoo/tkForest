package com.kdigital.ajaxtest.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.kdigital.ajaxtest.dto.GuestbookDTO;
import com.kdigital.ajaxtest.entity.GuestbookEntity;
import com.kdigital.ajaxtest.repository.GuestbookRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GuestbookService {
	
	final GuestbookRepository repository;

	/**
	 * 방명록의 모든 글을 조회
	 * @return
	 */
	public List<GuestbookDTO> selectAll() {
		List<GuestbookDTO> list = new ArrayList<>();
		
		List<GuestbookEntity> guestEntity = repository.findAll();
		
		// entity를 dto로 변환
		guestEntity.forEach((entity) -> list.add(GuestbookDTO.toDTO(entity)));
		
		return list;
	}

	/**
	 * 전달받은 dto를 DB에 저장
	 * @param dto
	 */
	public void inputGuestbook(GuestbookDTO dto) {
		GuestbookEntity entity = GuestbookEntity.toEntity(dto);
		
		repository.save(entity);
	}

	/**
	 * 모든 방명록 코드를 조회
	 * @return
	 */
	public List<GuestbookDTO> retrieveAll() {
		List<GuestbookDTO> list = new ArrayList<>();
		List<GuestbookEntity> entity = repository.findAll(Sort.by(Sort.Direction.DESC ,"seq"));
		
		entity.forEach((e) -> list.add(GuestbookDTO.toDTO(e)));
		
		return list;
	}

	/**
	 * DB에서 글 삭제
	 * @param seq
	 */
	@Transactional
	public void deleteGuestbook(Long seq, String pwd) {
		repository.deleteBySeqAndPwd(seq, pwd);
	}
}
