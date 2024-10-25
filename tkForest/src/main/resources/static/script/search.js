/**
 * 검색기능을 처리하는 스크립트
 */

// 이벤트 소스
let searchInput = document.getElementById("searchInput");  //검색어 입력
//let latestButton = document.getElementById("latest"); // 최신순 정렬
//let mostViewedButton = document.getElementById("mostViewed"); // 조회수 정렬


// searchInput.addEventListener('click', goto);
//latest.addEventListener('click', latestButton);
//mostViewed.addEventListener('click', mostViewedButton);

// 이벤트 핸들러
/** 
 * 검색창에서 유형+검색어 입력
*/
function goto() {
	let searchType = document.getElementById("searchType").value;
	let brand = document.getElementById("brand").value;
	let searchForm = document.getElementById("searchForm");
	
	searchForm.action='/board/boardList'
	searchForm.submit();
}

/** 
 * 페이징 시 current 페이지 요청 함수
*/
function pageFormSubmit(page) {
	document.getElementById("requestPage").value = page;
	document.getElementById("searchForm").submit();
}

/** 
 * 스위칭버튼(최신순과 조회수)
*/
function sortProducts(sortBy) {
   document.getElementById('sortBy').value = sortBy; // sortBy 값 설정
   document.getElementById('searchForm').submit();   // 폼 제출
}

