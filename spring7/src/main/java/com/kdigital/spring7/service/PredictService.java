package com.kdigital.spring7.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;

import com.kdigital.spring7.dto.Iris;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PredictService {

	@Value("${iris.predict.server}")
	String url;

	private final RestTemplate restTemplate; 

	public Map<String, Object> predictRest(Iris iris) {
		log.info("분석서버 주소 {}", url);
		log.info("Iris 데이터 {}", iris);

		Map<String, Object> result = new HashMap<>();

		try { 
			HttpHeaders headers = new HttpHeaders();

			headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

			ResponseEntity<Map> response = restTemplate.postForEntity(url, iris, Map.class);

			// 응답코드
			log.info("응답코드: {}", response.getStatusCode());
			result = response.getBody();

			log.info("예측한 분류 결과: {}", result.get("predict_result"));
		} catch (HttpClientErrorException | HttpServerErrorException e) {
			log.info("text : {}", e.getStatusText());
			log.info("code : {}", e.getStatusCode());
		}

		return result; // 정상결과 반환
	}
}

// 1) 유비콘을 실행한다. : uvicorn main:app --reload
// 2) 스프링을 실행한다.
// 3) 웹화면에서 데이터를 입력한다. ( 5.1, 3.5, 1.4, 0.2 )
