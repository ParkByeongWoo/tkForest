package com.tkForest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.tkForest.dto.ReplyDTO;
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.InquiryEntity;
import com.tkForest.entity.ReplyEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.InquiryRepository;
import com.tkForest.repository.ReplyRepository;
import com.tkForest.repository.SellerRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyService {

    final InquiryRepository inquiryRepository;
    final ReplyRepository replyRepository;
    final BuyerRepository buyerRepository;
    final SellerRepository sellerRepository;

    @Transactional
    public ReplyDTO replyInsert(ReplyDTO replyDTO) {
        // 부모의 인콰이어리 존재 여부 확인
        Optional<InquiryEntity> inquiryEntityOpt = inquiryRepository.findById(replyDTO.getInquiryNo());
        log.info("보내는 중");
        if (inquiryEntityOpt.isPresent()) {
            InquiryEntity inquiryEntity = inquiryEntityOpt.get();
            
            // 댓글 작성자의 종류(바이어/셀러)에 따라 picName 설정
            if ("BUYER".equalsIgnoreCase(replyDTO.getReplyWriterType())) {
                BuyerEntity buyerEntity = buyerRepository.findByBuyerMemberNo(replyDTO.getReplyWriter())
                    .orElseThrow(() -> new RuntimeException("Buyer not found"));
                replyDTO.setReplyWriter(buyerEntity.getPicName());  // 바이어의 picName으로 설정
            } else if ("SELLER".equalsIgnoreCase(replyDTO.getReplyWriterType())) {
                SellerEntity sellerEntity = sellerRepository.findBySellerMemberNo(replyDTO.getReplyWriter())
                    .orElseThrow(() -> new RuntimeException("Seller not found"));
                replyDTO.setReplyWriter(sellerEntity.getPicName());  // 셀러의 picName으로 설정
            }

            // ReplyEntity로 변환 후 저장
            ReplyEntity replyEntity = ReplyEntity.toEntity(replyDTO, inquiryEntity);
            ReplyEntity savedReplyEntity = replyRepository.save(replyEntity);

            return ReplyDTO.toDTO(savedReplyEntity, replyDTO.getInquiryNo());
        }
        return null;
    }



    // 모든 댓글 조회
    public List<ReplyDTO> replyAll(Integer inquiryNo) {
        // 인콰이어리 존재 여부 확인
        Optional<InquiryEntity> inquiryEntityOpt = inquiryRepository.findById(inquiryNo);
        if (inquiryEntityOpt.isEmpty()) {
            return new ArrayList<>();
        }

        // 인콰이어리의 댓글을 모두 조회
        List<ReplyEntity> replyEntityList = replyRepository.findAllByInquiryEntityOrderByReplyNoDesc(inquiryEntityOpt.get());

        // Entity -> DTO 변환
        return replyEntityList.stream()
                .map(entity -> ReplyDTO.toDTO(entity, inquiryNo))
                .toList();
    }
}
