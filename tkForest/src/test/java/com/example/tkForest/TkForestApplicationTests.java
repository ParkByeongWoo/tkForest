package com.example.tkForest;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.tkForest.TkForestApplication;
import com.tkForest.entity.SellerEntity;
import com.tkForest.repository.SellerRepository;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest(classes = TkForestApplication.class)
@Slf4j
class TkForestApplicationTests {

    @Autowired
    private SellerRepository sellerRepository;

    @Test
    public void testSellerFetch() {
        // 테스트에 필요한 셀러 NO
        String sellerMemberNo = "S250134";

        // 테스트 전용 데이터를 DB에 삽입하거나, 실제 데이터가 존재하는지 확인
        SellerEntity seller = sellerRepository.findById(sellerMemberNo)
            .orElseThrow(() -> new RuntimeException("Seller with ID " + sellerMemberNo + " not found"));

        // 셀러가 null이 아닌지 검증
        assertNotNull(seller);

        // 로그를 통해 결과 출력
        log.info("Fetched Seller: {}", seller);
    }
}

	
//	@Autowired 
//	private UserService userservice;
//	@Test
//	void contextLoads() {
//	}
//
//	void test_yeah () {
//		Object dto = userservice.existId("1", true);
//		log.info("회원: {}",dto.toString());
//	}
//	
//	@Autowired
//	private ReplyRepository repository;	
	

