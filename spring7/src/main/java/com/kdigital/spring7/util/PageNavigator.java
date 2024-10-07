package com.kdigital.spring7.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageNavigator {
	private final int pagePerGroup = 10;	// 그룹당 페이지수 (스프링에서 10개로 고정시
	private int pageLimit;					// 페이지당 글 개수
	private int page;						// 사용자가 요청한 페이지
	private int totalPages;					// 총 페이지 수 (글이 총 204개 21개)
	private int totalGroupCount;			// 총 그룹의 수
	private int currentGroup;				// 요청한 페이지가 속한 그룹
	private int startPageGroup;				// 현재 그룹의 첫 페이지
	private int endPageGroup;				// 현재 그룹의 마지막 페이지
	
	public PageNavigator(int pageLimit, int page, int totalPages) {
		// 멤버 초기화
		this.pageLimit  = pageLimit;
		this.page       = page;
		this.totalPages = totalPages;
		
		// 총 그룹수 계산
		// 10페이지 ==> 그룹이 1개, 11페이지 ==> 그룹이 2개
		totalGroupCount = totalPages / pagePerGroup; 
		totalGroupCount += (totalPages % pagePerGroup == 0) ? 0 : 1;
		
		// 사용자가 요청한 페이지의 첫번째 글번호와 마지막 글번호 계산
		startPageGroup = ((int)(Math.ceil((double)page / pageLimit)) - 1) * pageLimit + 1;
		endPageGroup   = (startPageGroup + pageLimit - 1) < totalPages
					     ? (startPageGroup + pageLimit -1)
				         : totalPages;
		
		// 검색과 함께 사용했는데 검색 결과가 하나도 없는 경우
		// startPageGroup = 1이고 endPageGroup = 0 이므로 endPageGroup = 1로 세팅
		if(endPageGroup == 0) endPageGroup = 1;
		
		// 요청한 페이지가 속한 그룹
		currentGroup = (page - 1) / pagePerGroup + 1;
	}
}






