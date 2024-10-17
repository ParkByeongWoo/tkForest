package com.tkForest.service;

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
    final ProductRepository productRepository; // 연관된 Repository
    final BuyerRepository buyerRepository;     // 연관된 Repository
    final SellerRepository sellerRepository;   // 연관된 Repository

    @Value("${user.inquiry.pageLimit}")
    private int pageLimit;

    @Value("${spring.servlet.multipart.location}")
    private String uploadPath;

    /**
     * DB에 게시글 저장
     * @param inquiryDTO : 저장해야하는 게시글
     */
    public void insertInquiry(InquiryDTO inquiryDTO) {
        log.info("저장 경로: {}", uploadPath);

        // 첨부 파일 처리
        String originalFileName = null;
        String savedFileName = null;

        // 파일 첨부 여부 확인
        if (!inquiryDTO.getUploadFile().isEmpty()) {
            savedFileName = FileService.saveFile(inquiryDTO.getUploadFile(), uploadPath);
            originalFileName = inquiryDTO.getUploadFile().getOriginalFilename();

            inquiryDTO.setOriginalFileName(originalFileName);
            inquiryDTO.setSavedFileName(savedFileName);
        }

        log.info("원본 파일명: {}", originalFileName);
        log.info("저장 파일명: {}", savedFileName);

        // 1) 연관된 엔티티 가져오기
        ProductEntity productEntity = productRepository.findById(inquiryDTO.getProduct().getProductNo())  // DTO 내부의 값 접근
                .orElseThrow(() -> new RuntimeException("Product not found"));

        BuyerEntity buyerEntity = buyerRepository.findById(inquiryDTO.getBuyer().getBuyerMemberNo())  // DTO 내부의 값 접근
                .orElseThrow(() -> new RuntimeException("Buyer not found"));

        SellerEntity sellerEntity = sellerRepository.findById(inquiryDTO.getSeller().getSellerMemberNo())  // DTO 내부의 값 접근
                .orElseThrow(() -> new RuntimeException("Seller not found"));

        // 2) InquiryEntity로 변환
        InquiryEntity inquiryEntity = InquiryEntity.toEntity(inquiryDTO, productEntity, buyerEntity, sellerEntity);

        // 3) save()로 데이터 저장
        inquiryRepository.save(inquiryEntity);
    }

//    /**
//     * 인콰이어리 목록 요청 (검색기능 추가)
//     * @param pageable 
//     * @param searchWord 
//     * @param searchItem 
//     * @return
//     */
//    public Page<InquiryDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
//        int page = pageable.getPageNumber() - 1;
//        // -1을 한 이유 : DB page의 위치의 값은 0부터 시작하므로
//        // 사용자가 1페이지를 요청하면 DB페이지를 0페이지를 가져와야 한다.
//
//        Page<InquiryEntity> entityList = null;
//
//        switch(searchItem) {
//            case "subject":
//                entityList = inquiryRepository.findBySubjectContains(
//                        searchWord, 
//                        PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "inquiryNo")));
//                break;
//            case "buyerMemberNo":
//                entityList = inquiryRepository.findBybuyerMemberNoContains(
//                        searchWord, 
//                        PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "inquiryNo")));
//                break;
//            case "contents":
//                entityList = inquiryRepository.findBycontentsContains(
//                        searchWord, 
//                        PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "inquiryNo")));
//                break;
//        }
//
//        // 페이징 형태의 list로 변환
//        Page<InquiryDTO> list = entityList.map(
//        	    (inquiry) -> new InquiryDTO(
//        	        inquiry.getInquiryNo(),
//        	        ProductDTO.toDTO(inquiry.getProductEntity()),  // ProductEntity를 ProductDTO로 변환
//        	        BuyerDTO.toDTO(inquiry.getBuyerEntity()),  // BuyerEntity를 BuyerDTO로 변환
//        	        SellerDTO.toDTO(inquiry.getSellerEntity()),  // SellerEntity를 SellerDTO로 변환
//        	        inquiry.getSubject(),
//        	        inquiry.getContents(),
//        	        inquiry.getOfferSendDate(),
//        	        inquiry.getOfferExpireDate(),
//        	        inquiry.getOrderQuantity(),
//        	        inquiry.getOrderUnitEtc(),
//        	        inquiry.getExpectedPrice(),
//        	        inquiry.getOriginalFileName(),
//        	        inquiry.getSavedFileName()
//        	    )
//        	);
//
//
//        return list;
//    }

//    /**
//     * PK에 해당하는 inquiryNo 값을 이용해 글 한 개 조회
//     * @param inquiryNo
//     * @return
//     */
//    public InquiryDTO selectOne(Integer inquiryNo) {
//        Optional<InquiryEntity> entity = inquiryRepository.findById(inquiryNo);
//
//        // 데이터를 꺼내 InquiryDTO로 변환
//        if (entity.isPresent()) {
//            InquiryEntity temp = entity.get();
//            // ProductDTO, BuyerDTO, SellerDTO로 변환해야 함
//            ProductDTO productDTO = ProductDTO.toDTO(temp.getProductEntity());
//            BuyerDTO buyerDTO = BuyerDTO.toDTO(temp.getBuyerEntity());
//            SellerDTO sellerDTO = SellerDTO.toDTO(temp.getSellerEntity());
//
//            return InquiryDTO.toDTO(temp, productDTO, buyerDTO, sellerDTO);
//        }
//        return null;
//    }

    /**
     * 전달받은 글번호(inquiryNo)을 DB 에서 삭제
     * @param inquiryNo
     */
    @Transactional
    public void deleteOne(Integer inquiryNo) {
        // 글번호에 해당하는 글이 존재하는지 읽어옴.
        Optional<InquiryEntity> entity = inquiryRepository.findById(inquiryNo);

        if(entity.isPresent()) {
            InquiryEntity inquiry = entity.get();
            String savedFileName = inquiry.getSavedFileName();

            // 첨부파일이 있으면 파일을 삭제하고, DB에서도 삭제
            if(savedFileName != null) {
                String fullPath = uploadPath + "/" + savedFileName;
                boolean result = FileService.deleteFile(fullPath);

                log.info("파일 삭제 여부 : {}", result); // true이면 삭제됨
            }

            inquiryRepository.deleteById(inquiryNo);
        }
    }

}
