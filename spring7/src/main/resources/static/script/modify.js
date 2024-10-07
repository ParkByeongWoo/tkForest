// 이벤트 소스
let deleteBtn = document.getElementById("deleteBtn");
let updateBtn = document.getElementById("updateBtn");

// 이벤트 핸들러와 연결
// 로그인한 사람과 글쓴이가 다르면 deleteBtn과 updateBtn이 없기 때문에(null)
// 이벤트 설정을 할 수 없다.
if(deleteBtn != null) deleteBtn.addEventListener('click', goto);
if(updateBtn != null) updateBtn.addEventListener('click', goto);

// 이벤트 핸들러
function goto() {
    let target = this.getAttribute("id");
    // let boardNum = this.getAttribute("data-no");
	let boardNum   = document.getElementById("boardNum").value;
	let searchItem = document.getElementById("searchItem").value;
	let searchWord = document.getElementById("searchWord").value;
	
    let go = '';

    switch (target) {
        case "updateBtn": go = "boardUpdate"; break;
        case "deleteBtn": 
			if(!confirm("정말 삭제할까요?")) return;
			go = "boardDelete"; 
			break;
    }

	let modifyForm = document.getElementById("modifyForm");
	modifyForm.action = `/board/${go}`;
	modifyForm.submit();
	
    // let url = `/board/${go}?boardNum=${boardNum}&searchItem=${searchItem}&searchWord=${searchWord}`;	
    // location.replace(url);   // GET 요청
}

