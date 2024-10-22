package com.tkForest.service;

import java.util.ArrayList;
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

import com.tkForest.dto.ProductDTO;
import com.tkForest.entity.ProductEntity;
import com.tkForest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service

@Slf4j
public class RecService {
	
	@Value("${rec.server}")
	String url;
	
	private final RestTemplate restTemplate;
	private final ProductRepository productRepository;
//	
//	public Map<String, Object> recList(String buyerMemberNo) {
//
//		Map<String, Object> result = new HashMap<>();
//
//		try { 
//			HttpHeaders headers = new HttpHeaders();
//
//			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
//			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
//			buyerMemberNo = buyerMemberNo.substring(1);
//			ResponseEntity<Map> response = restTemplate.postForEntity(url, buyerMemberNo, Map.class);
//
//			// 응답결과
//			result = response.getBody();
//						
//			System.out.println("result의 정체: ");
//
//		} catch (HttpClientErrorException | HttpServerErrorException e) {
//			log.info("text : {}", e.getStatusText());
//			log.info("code : {}", e.getStatusCode());
//		}
//
//		return result; // 정상결과 반환
//	}

	public RecService(RestTemplate restTemplate, ProductRepository productRepository) {
        this.restTemplate = restTemplate;
        this.productRepository = productRepository;
    }

	public List<ProductDTO> recList(String buyerMemberNo) {
	    Map<String, Object> result = new HashMap<>();

	    try {
	        if (buyerMemberNo == null || buyerMemberNo.length() < 2) {
	            throw new IllegalArgumentException("Invalid buyerMemberNo");
	        }
	        // buyerMemberNo의 첫 글자 제거
	        buyerMemberNo = buyerMemberNo.substring(1);
	        log.info(buyerMemberNo);

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
            ProductEntity temp = entity.get();
            ProductDTO dto = ProductDTO.toDTO(temp, temp.getSellerEntity().getSellerMemberNo());
	        productDTOList.add(dto);
	    }
	    return productDTOList; // 정상 결과 반환
	}
}

