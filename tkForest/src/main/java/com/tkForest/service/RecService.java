package com.tkForest.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.tkForest.dto.BCategoryDTO;
import com.tkForest.dto.B_LikeDTO;
import com.tkForest.dto.CategoryDTO;
import com.tkForest.dto.PCategoryDTO;
import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.BCategoryEntity;
import com.tkForest.entity.B_LikeEntity;
import com.tkForest.entity.CategoryEntity;
import com.tkForest.entity.InquiryEntity;
import com.tkForest.entity.ProductEntity;
import com.tkForest.repository.BCategoryRepository;
import com.tkForest.repository.B_LikeRepository;
import com.tkForest.repository.BuyerRepository;
import com.tkForest.repository.CategoryRepository;
import com.tkForest.repository.InquiryRepository;
import com.tkForest.repository.PCategoryRepository;
import com.tkForest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class RecService {
	
	@Value("${rec.server}")
	String url;
	private final B_LikeRepository bLikeRepository;
	private final InquiryRepository inquiryRepository;
	private final RestTemplate restTemplate;
	private final ProductRepository productRepository;
	private final BCategoryRepository bCategoryRepository; 
	private final CategoryRepository categoryRepository;
	private final BuyerRepository buyerRepository;

	public RecService(
			B_LikeRepository bLikeRepository
			, InquiryRepository inquiryRepository
			, RestTemplate restTemplate
			, ProductRepository productRepository
			, BCategoryRepository bCategoryRepository
			, CategoryRepository categoryRepository
			, BuyerRepository buyerRepository) {
        this.bLikeRepository = bLikeRepository;
        this.inquiryRepository = inquiryRepository;
		this.restTemplate = restTemplate;
        this.productRepository = productRepository;
        this.bCategoryRepository = bCategoryRepository;
        this.categoryRepository = categoryRepository;
        this.buyerRepository = buyerRepository;
	}

	public List<ProductDTO> recList(String buyerMemberNo) {
	    Map<String, Object> result = new HashMap<>();

	    try {
	        if (buyerMemberNo == null || buyerMemberNo.length() < 2) {
	            throw new IllegalArgumentException("Invalid buyerMemberNo");
	        }
	        List<B_LikeEntity> bLikeEntity = bLikeRepository.findByLikefromBuyerEntity_BuyerMemberNoContains(buyerMemberNo);
	        List<InquiryEntity> inquiryEntity = inquiryRepository.findByBuyerEntity_BuyerMemberNoContains(buyerMemberNo);
	        System.out.println(bLikeEntity.size());
	        System.out.println(inquiryEntity.size());

	        int check;
	        if (bLikeEntity.size()!=0 || inquiryEntity.size()!=0) {
		        // buyerMemberNo의 첫 글자 제거
		        buyerMemberNo = buyerMemberNo.substring(1);
		        log.info(buyerMemberNo);
		        log.info("알고2");
		        check = 2;
		    } else if (!(bLikeEntity.size()==0 || inquiryEntity.size()==0)) {
		        check = 1;
		        log.info(buyerMemberNo);
		        log.info("알고1");
		    }
	        // HTTP 헤더 설정 (JSON 타입으로 전송)
	        HttpHeaders headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

	        // 요청 데이터 설정 (Map을 사용하여 JSON 데이터 생성)
	        Map<String, String> requestBody = new HashMap<>();
	        requestBody.put("buyerMemberNo", buyerMemberNo);

	        // HttpEntity에 헤더와 데이터를 설정 (JSON으로 전송)
	        HttpEntity<Map<String, String>> entity = new HttpEntity<>(requestBody, headers);

	        // 외부 서버에 POST 요청
	        ResponseEntity<Map<String, Object>> response = restTemplate.exchange(url, HttpMethod.POST, entity, new ParameterizedTypeReference<Map<String, Object>>() {});

	        // 응답 결과 처리
	        result = response.getBody();

	        System.out.println("결과: " + result);

	    } catch (HttpClientErrorException | HttpServerErrorException e) {
	        log.error("Response Body: {}", e.getResponseBodyAsString());
	        log.info("Error occurred: StatusText: {}, StatusCode: {}", e.getStatusText(), e.getStatusCode());
	    } catch (IllegalArgumentException e) {
	        log.error("Invalid input: {}", e.getMessage());
	    }
        List<Integer> recommendations = (List<Integer>) result.get("recommendations");

	    List<ProductDTO> productDTOList = new ArrayList<>();
	    
	    for (Integer productNo : recommendations) {
            Optional<ProductEntity> entity = productRepository.findById(productNo);
            ProductEntity product = entity.get();
            ProductDTO dto = new ProductDTO(
					product.getProductNo(),
					product.getSellerEntity().getSellerMemberNo(), // ******혹시 나중에 오류나면 확인해보시길******
					product.getRegistrationDate(),
					product.getProductName(),
					product.getBrand(),
					product.getSellerEntity().getCompanyName());
            productDTOList.add(dto);
	    }
	    return productDTOList; // 정상 결과 반환
	}
	
	public List<BCategoryDTO> recCategory(String buyerMemberNo){
		List<BCategoryEntity> bCategoryEntityList = bCategoryRepository.findByBuyerEntity_BuyerMemberNo(buyerMemberNo);
		List<BCategoryDTO> bCategoryDTOList = new ArrayList<>();
		for (BCategoryEntity entity : bCategoryEntityList) {
			bCategoryDTOList.add(BCategoryDTO.toDTO(entity, buyerMemberNo, entity.getCategoryEntity().getCategoryNo()));
		}
		
		return bCategoryDTOList;
	}
	public List<CategoryDTO> category(List<BCategoryDTO> bCategoryDTOList){
		List<CategoryDTO> categoryDTOList = new ArrayList<>();
		for (BCategoryDTO bCategoryDTO : bCategoryDTOList) {
			Optional<CategoryEntity> categoryEntity = categoryRepository.findById(bCategoryDTO.getCategoryNo());
			CategoryEntity temp = categoryEntity.get();
			CategoryDTO dto = CategoryDTO.toDTO(temp);
			categoryDTOList.add(dto);
		}
		return categoryDTOList;
	}
	
	public List<String> keyword(String buyerMemberNo){
		String keywords = buyerRepository.findConcernKeywordByBuyerMemberNo(buyerMemberNo);
		List<String> keywordList = Arrays.asList(keywords.split(","));
		return keywordList;
	}
	
}

