package com.tkForest.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tkForest.dto.BuyerDTO;
import com.tkForest.dto.InquiryDTO;
import com.tkForest.dto.ProductDTO;
import com.tkForest.dto.SellerDTO;
import com.tkForest.entity.BuyerEntity;
import com.tkForest.entity.InquiryEntity;
import com.tkForest.entity.ProductEntity;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.InquiryRepository;
import com.tkForest.repository.ProductRepository;
import com.tkForest.repository.SellerRepository;
import com.tkForest.util.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InquiryService {

    final InquiryRepository inquiryRepository;
    private final BuyerRepository buyerRepository;
    private final SellerRepository sellerRepository;
    private final ProductRepository productRepository;

    /*
     * 리스트 조회
     */
    // 셀러의 인콰이어리 리스트 조회
    public List<InquiryDTO> findInquiriesBySeller(String sellerMemberNo) {
        List<InquiryEntity> inquiryEntities = inquiryRepository.findBySellerEntity_SellerMemberNo(sellerMemberNo);
        
        // Entity -> DTO 변환
        return inquiryEntities.stream()
                .map(inquiry -> InquiryDTO.toDTO(
                    inquiry,
                    inquiry.getProductEntity().getProductNo(),
                    inquiry.getBuyerEntity().getBuyerMemberNo(),
                    inquiry.getSellerEntity().getSellerMemberNo()
                ))
                .toList();
    }

    // 바이어의 인콰이어리 리스트 조회
    public List<InquiryDTO> findInquiriesByBuyer(String buyerMemberNo) {
        List<InquiryEntity> inquiryEntities = inquiryRepository.findByBuyerEntity_BuyerMemberNo(buyerMemberNo);
        
        // Entity -> DTO 변환
        return inquiryEntities.stream()
                .map(inquiry -> InquiryDTO.toDTO(
                    inquiry,
                    inquiry.getProductEntity().getProductNo(),
                    inquiry.getBuyerEntity().getBuyerMemberNo(),
                    inquiry.getSellerEntity().getSellerMemberNo()
                ))
                .toList();
    }
    
    /*
     * 상세 조회
     */
    // 인콰이어리 번호로 상세 정보 조회
    public InquiryDTO findInquiryByNo(Integer inquiryNo) {
        Optional<InquiryEntity> inquiryEntity = inquiryRepository.findById(inquiryNo);

        // 엔티티가 존재할 경우에만 DTO로 변환
        if (inquiryEntity.isPresent()) {
            InquiryEntity entity = inquiryEntity.get();
            return InquiryDTO.toDTO(
                entity,
                entity.getProductEntity().getProductNo(),
                entity.getBuyerEntity().getBuyerMemberNo(),
                entity.getSellerEntity().getSellerMemberNo()
            );
        }
        return null;
    }
    
    /*
     * 작성
     */
    @Transactional
    public void insertInquiry(InquiryDTO inquiryDTO) {
        log.info("Inserting inquiry with buyerMemberNo: {}", inquiryDTO.getBuyerMemberNo());
        log.info("Inserting inquiry with sellerMemberNo: {}", inquiryDTO.getSellerMemberNo());
        
        // Buyer 조회 (buyerMemberNo로 조회)
        BuyerEntity buyerEntity = buyerRepository.findByBuyerMemberNo(inquiryDTO.getBuyerMemberNo())
            .orElseThrow(() -> new RuntimeException("Buyer not found with buyerMemberNo: " + inquiryDTO.getBuyerMemberNo()));
        
        // Seller 조회
        SellerEntity sellerEntity = sellerRepository.findBySellerMemberNo(inquiryDTO.getSellerMemberNo())
            .orElseThrow(() -> new RuntimeException("Seller not found"));

        // Product 조회
        ProductEntity productEntity = productRepository.findById(inquiryDTO.getProductNo())
            .orElseThrow(() -> new RuntimeException("Product not found"));

        // DTO -> Entity 변환 및 저장
        InquiryEntity inquiryEntity = InquiryEntity.toEntity(inquiryDTO, productEntity, buyerEntity, sellerEntity);
        inquiryRepository.save(inquiryEntity);
    }

    
   

}

