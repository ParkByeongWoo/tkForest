package com.kdigital.spring7;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.kdigital.spring7.entity.BoardEntity;
import com.kdigital.spring7.repository.BoardRepository;

@SpringBootTest
class Spring7ApplicationTests {

	@Test
	void contextLoads() {
	}
	
	@Autowired
	private BoardRepository repository;	
	
	// 게시글 여러 개를 삽입하는 테스트 코드
	@Test
	void testInsertBoard() {
		String[] w = {"이몽룡", "성춘향", "김방자", "전우치", "콩쥐", "팥쥐"};
		String[] c = {"나리나리 개나리 입에따다 물고요", 
				"송아지 송아지 얼룩 송아지 엄마소도 얼룩소", 
				"파란하늘 파란하늘 꿈이 드리운 푸른 언덕에..." ,
				"학교 종이 땡땡땡 어서모이자", 
				"동해물과 백두산이 마르고 닳도록 하느님이 보우하사", 
				"이 기상과 이 맘으로 충성을 다하여", 
				"가을 하늘 공활한데 높고 구름 없이", 
				"오늘은 목요일, 내일은 금요일이군요", 
				"날씨가 매우 좋네요.", 
				"아무 말이나 해 봅시다"};
		for(int i=0; i<135; ++i) {
			int index = (int)(Math.random() * w.length);
			String writer = w[index];
			
			index = (int)(Math.random() * c.length);
			String content = c[index];
			String title = "제목: " + content;
			
			BoardEntity entity = new BoardEntity();
			entity.setBoardWriter(writer);
			entity.setBoardTitle(title);
			entity.setBoardContent(content);
			
			repository.save(entity);
		}
	}

}
