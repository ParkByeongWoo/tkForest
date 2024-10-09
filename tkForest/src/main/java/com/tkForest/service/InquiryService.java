package com.tkForest.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.tkForest.dto.InquiryDTO;
import com.tkForest.entity.InquiryEntity;
import com.tkForest.repository.InquiryRepository;
import com.tkForest.util.FileService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class InquiryService {

	final InquiryRepository inquiryRepository;

	// 페이징할 때 한 페이지 출력할 글 개수
	@Value("${user.inquiry.pageLimit}")
	private int pageLimit;	

	// 업로드된 파일이 저장될 디렉토리 경로를 읽어옴
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

		// 파일 첨부여부 확인
		if(!inquiryDTO.getUploadFile().isEmpty()) {
			savedFileName = FileService.saveFile(inquiryDTO.getUploadFile(), uploadPath);
			originalFileName = inquiryDTO.getUploadFile().getOriginalFilename();

			inquiryDTO.setOriginalFileName(originalFileName);
			inquiryDTO.setSavedFileName(savedFileName);
		}

		log.info("원본 파일명: {}", originalFileName);
		log.info("저장 파일명: {}", savedFileName);

		// 1) Entity로 변환
		InquiryEntity inquiryEntity = InquiryEntity.toEntity(inquiryDTO);

		// 2) save()로 데이터 저장
		inquiryRepository.save(inquiryEntity);
	}
	
	/**
	 * 게시글 목록 요청 (검색기능 추가)
	 * @param pageable 
	 * @param searchWord 
	 * @param searchItem 
	 * @return
	 */
	public Page<InquiryDTO> selectAll(Pageable pageable, String searchItem, String searchWord) {
		int page = pageable.getPageNumber() - 1;
		// -1을 한 이유 : DB page의 위치의 값은 0부터 시작하므로
		// 사용자가 1페이지를 요청하면 DB페이지를 0페이지를 가져와야 한다.

		// List<inquiryDTO> list = new ArrayList<>();

		// 3) 페이징이 추가된 조회
		Page<InquiryEntity> entityList = null;

		switch(searchItem) {
		case "inquiryTitle"   :
			entityList = inquiryRepository.findBySubjectContains(
					searchWord, 
					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "inquiryNum") ));
			break;
		case "inquiryWriter"  :
			entityList = inquiryRepository.findBybuyerMemberNoContains(
					searchWord, 
					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "inquiryNum") ));
			break;
		case "inquiryContent" :
			entityList = inquiryRepository.findBycontentsContains(
					searchWord, 
					PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "inquiryNum") ));
			break;
		}

		/* 2) 검색기능이 추가된 조회
		List<InquiryEntity> entityList = null;

		switch(searchItem) {
		case "inquiryTitle"   :
			entityList = inquiryRepository.findByInquiryTitleContains(searchWord, Sort.by(Sort.Direction.DESC, "inquiryNum"));
			break;
		case "inquiryWriter"  :
			entityList = inquiryRepository.findByInquiryWriterContains(searchWord, Sort.by(Sort.Direction.DESC, "inquiryNum"));
			break;
		case "inquiryContent" :
			entityList = inquiryRepository.findByInquiryContentContains(searchWord, Sort.by(Sort.Direction.DESC, "inquiryNum"));
			break;
		}
		 */


		// 1) 1단계 - 단순 조회
		// List<InquiryEntity> entityList = inquiryRepository.findAll(Sort.by(Sort.Direction.DESC, "inquiryNum"));

		// Entity를 DTO로 바꾸는 작업 수행

		Page<InquiryDTO> list = null;

		// 일반 기능만 가지고 있는 형태
		// entityList.forEach((entity) -> list.add(inquiryDTO.toDTO(entity)));

		// 페이징 형태의 list로 변환
		// 목록에서 사용할 필요한 데이터만 간추림(생성자 만듦)
		list = entityList.map(
				(inquiry) -> new InquiryDTO(
						inquiry.getProductNo(),
						inquiry.getProductNo(),
						inquiry.getBuyerMemberNo(),
						inquiry.getBuyerMemberNo(),
						inquiry.getSubject(),
						inquiry.getContents(),
						inquiry.getOfferExpireDate(),
						inquiry.getOfferExpireDate(),
						inquiry.getOrderQuantity(),
						inquiry.getOrderUnitEtc(),
						inquiry.getExpectedPrice(),
						inquiry.getOriginalFileName()
						)
				);

		return list;
	}
	/**
	 * PK에 해당하는 inquiryNum 값을 이용해 글 한 개 조회
	 * @param inquiryNum
	 * @return
	 */
	public InquiryDTO selectOne(Integer inquiryNum) {
		Optional<InquiryEntity> entity = inquiryRepository.findById(inquiryNum);

		// 데이터를 꺼내 InquiryDTO로 변환
		if(entity.isPresent()) {
			InquiryEntity temp = entity.get();
			return InquiryDTO.toDTO(temp);
		}
		return null;
	}

	/**
	 * 전달받은 글번호(inquiryNum)을 DB 에서 삭제
	 * @param inquiryNum
	 */
	@Transactional
	public void deleteOne(Integer inquiryNum) {
		// 글번호에 해당하는 글이 존재하는지 읽어옴.
		Optional<InquiryEntity> entity = inquiryRepository.findById(inquiryNum);

		if(entity.isPresent()) {
			InquiryEntity inquiry = entity.get();
			String savedFileName = inquiry.getSavedFileName();

			// 첨부파일이 있으면 파일을 삭제하고, DB에서도 삭제
			if(savedFileName != null) {
				String fullPath = uploadPath + "/" + savedFileName;
				boolean result = FileService.deleteFile(fullPath);

				log.info("파일 삭제 여부 : {}", result); // true이면 삭제됨
			}

			inquiryRepository.deleteById(inquiryNum);
		}
	}



	
}
