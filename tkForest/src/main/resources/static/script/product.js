/**
 * 상품 관련
 * - 카테고리.json
 */


let categoryData = {};

$(function(){
		
	// categories.json 파일에서 카테고리 데이터 불러오기
	fetchCategories().then(() => {
		
		initializeModal();   // 카테고리 json 불러온 후 모달창 초기화

	});

	
});


// categories.json 파일에서 카테고리 데이터를 불러오는 함수
function fetchCategories() {
    return fetch('/categories.json')
        .then(response => response.json())
        .then(data => {
            categoryData = data; // 불러온 데이터를 categoryData에 저장
        })
        .catch(error => console.error('카테고리 데이터를 불러오는 중 오류 발생:', error));
}


// 모달 초기화 함수
function initializeModal() {
	
    const modal = $('#categoryModal');
    const openModalBtn = $('#openModalBtn');
    const closeModalBtn = $('.close-btn');

    const CATEGORY1 = $('#CATEGORY1');
    const CATEGORY2 = $('#CATEGORY2');
    const CATEGORY3 = $('#CATEGORY3');
    const selectedCategory = $('#selectedCategory');    

    // 모달 열기 버튼 클릭 시
    openModalBtn.on('click', function() {
		event.preventDefault();
        modal.show();
    });
	
	// 초기화 버튼 클릭 시 선택된 카테고리 비우기
		    $('#clearCategoryBtn').on('click', function() {
				event.preventDefault();
		        $('#selectedCategoryList').text('');  // 선택된 카테고리 텍스트 초기화
		        $('#selectedCategory').val('');       // 숨겨진 input 값 초기화
		        $('#hiddenCategoryInputs').empty();   // hidden input 요소 초기화
		    });

    // 모달 닫기 버튼 클릭 시
    closeModalBtn.on('click', function() {
		event.preventDefault();
        modal.hide();
    });

    // 모달 영역 외부 클릭 시 모달 닫기
    $(window).on('click', function(event) {
        if (event.target === modal[0]) {
            modal.hide();
        }
    });

    // CATEGORY1 로드
    loadCategory1(CATEGORY1);

    // CATEGORY1 변경 시 CATEGORY2 업데이트
    CATEGORY1.on('change', function() {
        updateCategory2(CATEGORY1, CATEGORY2, CATEGORY3);
    });

    // CATEGORY2 변경 시 CATEGORY3 업데이트
    CATEGORY2.on('change', function() {
        updateCategory3(CATEGORY1, CATEGORY2, CATEGORY3);
    });

    // 카테고리 추가 버튼 클릭 시
    $('#addCategoryBtn').on('click', function() {
		event.preventDefault();
        addCategory(CATEGORY1, CATEGORY2, CATEGORY3, selectedCategory, modal);
    });
}

// CATEGORY1 로드 함수
function loadCategory1(CATEGORY1) {
    for (let category in categoryData) {
        let option = $('<option></option>')
            .text(category)
            .val(category);
        CATEGORY1.append(option);
    }
}

// CATEGORY2 업데이트 함수
function updateCategory2(CATEGORY1, CATEGORY2, CATEGORY3) {
    CATEGORY2.empty().append('<option value="">선택 2</option>'); // 초기화
    CATEGORY3.empty().append('<option value="">선택 3</option>'); // 초기화
    let selectedMain = CATEGORY1.val();
    if (selectedMain) {
        let subCategories = categoryData[selectedMain];
        for (let sub in subCategories) {
            let option = $('<option></option>')
                .text(sub)
                .val(sub);
            CATEGORY2.append(option);
        }
    }
}

// CATEGORY3 업데이트 함수
function updateCategory3(CATEGORY1, CATEGORY2, CATEGORY3) {
    CATEGORY3.empty().append('<option value="">선택 3</option>'); // 초기화
    let selectedMain = CATEGORY1.val();
    let selectedSub = CATEGORY2.val();
    if (selectedSub) {
        let detailCategories = categoryData[selectedMain][selectedSub];
        detailCategories.forEach(function(detail) {
            let option = $('<option></option>')
                .text(detail)
                .val(detail);
            CATEGORY3.append(option);
        });
    }
}

// 카테고리 추가 함수
function addCategory(CATEGORY1, CATEGORY2, CATEGORY3, selectedCategory, modal) {
    let categoryText = '';

    // CATEGORY3이 선택된 경우, CATEGORY3만 추가
    if (CATEGORY3.val()) {
        categoryText = CATEGORY3.val(); // 3단계 카테고리 추가
    }
    // CATEGORY2가 선택된 경우, CATEGORY2만 추가
    else if (CATEGORY2.val()) {
        categoryText = CATEGORY2.val(); // 2단계 카테고리 추가
    }
    // CATEGORY1이 선택된 경우, CATEGORY1만 추가
    else if (CATEGORY1.val()) {
        categoryText = CATEGORY1.val(); // 1단계 카테고리 추가
    }

    if (categoryText) {
        // 선택된 카테고리 표시 영역
        const selectedCategoryList = $('#selectedCategoryList');

        // 선택된 카테고리 텍스트 추가 (이미 있는 텍스트에 쉼표로 구분)
        let currentText = selectedCategoryList.text();
        if (currentText) {
            selectedCategoryList.text(currentText + ', ' + categoryText); // 기존 텍스트에 쉼표와 함께 추가
        } else {
            selectedCategoryList.text(categoryText); // 첫 번째 항목일 경우 쉼표 없이 추가
        }

        // 선택된 카테고리를 ,로 구분하여 입력 필드에 표시
        let currentCategories = selectedCategory.val();
        selectedCategory.val(currentCategories ? currentCategories + ', ' + categoryText : categoryText);
		
		// 동적으로 hidden input 추가 (서버로 전달할 데이터)
		        const hiddenCategoryInputs = $('#hiddenCategoryInputs');
		        const newHiddenInput = $('<input>')
		            .attr('type', 'hidden')
		            .attr('name', 'categoryNames') // 배열로 전송할 수 있도록 설정
		            .val(categoryText);
		        hiddenCategoryInputs.append(newHiddenInput);
    }
	
    
			/*
	selectedCategory.val(categoryText);
    
	// 숨겨진 입력 필드에 카테고리 값 설정
	$('#selectedCategoryInput').val(categoryText);
	*/
	
	// modal.hide(); // 카테고리 선택 후 모달 닫기

}