package com.tkForest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tkForest.dto.ReplyDTO;
import com.tkForest.entity.InquiryEntity;
import com.tkForest.entity.ReplyEntity;
import com.tkForest.repository.InquiryRepository;
import com.tkForest.repository.ReplyRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {
	final InquiryRepository inquiryRepository;
	final ReplyRepository replyRepository;
	
	@Transactional
	public ReplyDTO replyInsert(ReplyDTO replyDTO) {
		// 부모의 게시글이 존재하는 여부 확인
		Optional<InquiryEntity> InquiryEntity = inquiryRepository.findById(replyDTO.getInquiryNo());
		if(InquiryEntity.isPresent()) {
			InquiryEntity entity = InquiryEntity.get();  
			System.out.println(entity);
			ReplyEntity replyEntity = ReplyEntity.toEntity(replyDTO, entity);
			
			ReplyEntity temp = replyRepository.save(replyEntity);
			return ReplyDTO.toDTO(temp, replyDTO.getInquiryNo());
		}
		return null;
	}

	public List<ReplyDTO> replyAll(Integer inquiryNo) {
		Optional<InquiryEntity> inquiryEntity = inquiryRepository.findById(inquiryNo);
		
		List<ReplyEntity> replyEntityList 
			= replyRepository.findAllByInquiryEntityOrderByReplyNoDesc(inquiryEntity);
		
		/* Entity --> DTO로 변환 */
		List<ReplyDTO> replyDTOList = new ArrayList<>();
		
		replyEntityList.forEach(
				(entity) -> replyDTOList.add(ReplyDTO.toDTO(entity, inquiryNo)));
		
		System.out.println("=================" + replyDTOList);
		return replyDTOList;
	}

	
}









