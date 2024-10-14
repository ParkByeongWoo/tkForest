package com.example.tkForest;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tkForest.dto.SellerDTO;
import com.tkForest.service.UserService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
class TkForestApplicationTests {

	@Autowired 
	private UserService userservice;
	
	@Test
	void contextLoads() {
	}

	@Test
	void test_yeah () {
		Object dto = userservice.existId("1", true);
		log.info("회원: {}",dto.toString());
	}
}
