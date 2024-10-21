package com.tkForest.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.tkForest.dto.ProductDTO;
import com.tkForest.repository.ProductRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class RecService {
	
	@Value("{rec.server}")
	String url;
	
	private final RestTemplate restTemplate;
	private final ProductRepository productRepository;
	
	public Map<String, Object> recList(String buyerMemberNo) {

		Map<String, Object> result = new HashMap<>();

		try { 
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			buyerMemberNo = buyerMemberNo.substring(1);
			ResponseEntity<Map> response = restTemplate.postForEntity(url, buyerMemberNo, Map.class);

			// 응답결과
			result = response.getBody();
						
			System.out.println("result의 정체: ");

		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.info("text : {}", e.getStatusText());
			log.info("code : {}", e.getStatusCode());
		}

		return result; // 정상결과 반환
	}
}

