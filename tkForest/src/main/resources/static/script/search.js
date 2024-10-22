/**
 * 검색기능을 처리하는 스크립트
 */

// 이벤트 소스
let searchInput = document.getElementById("searchInput");

searchInput.addEventListener('click', goto);

// 이벤트 핸들러
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







