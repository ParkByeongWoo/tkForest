/**
 *  인콰이어리 작성 내 셀러 검색 ajax
 */

/*
document.addEventListener('DOMContentLoaded', function() {
    const searchSellerBtn = document.getElementById('searchSellerBtn');

    // 버튼이 실제로 존재하는지 확인
    if (searchSellerBtn) {
        searchSellerBtn.addEventListener('click', function() {
            const sellerMemberNo = document.getElementById('sellerMemberNo').value;

            if (sellerMemberNo) {
                // AJAX 요청
                fetch(`/user/${sellerMemberNo}/products`)
                    .then(response => response.json())
                    .then(data => {
                        const productSelect = document.getElementById('productNo');
                        productSelect.innerHTML = ''; // 기존 옵션 제거

                        // 상품 목록 추가
                        data.forEach(product => {
                            const option = document.createElement('option');
                            option.value = product.productNo;
                            option.textContent = product.productName;
                            productSelect.appendChild(option);
                        });
                    })
                    .catch(error => console.error('Error:', error));
            } else {
                alert('셀러 멤버 번호를 입력하세요.');
            }
        });
    } else {
        console.error('searchSellerBtn 버튼을 찾을 수 없습니다.');
    }
	
	fetch(`/user/${sellerMemberNo}/products`)
	    .then(response => {
	        // 응답이 JSON 형식인지 확인
	        const contentType = response.headers.get("content-type");
	        if (contentType && contentType.indexOf("application/json") !== -1) {
	            return response.json();  // JSON 응답 처리
	        } else {
	            return response.text();  // JSON이 아닌 경우 텍스트로 처리 (에러 응답 처리)
	        }
	    })
	    .then(data => {
	        // JSON 응답 처리
	        if (typeof data === 'string') {
	            console.error("Error: Received HTML instead of JSON", data);
	            alert("서버에서 올바른 응답을 받지 못했습니다. 다시 시도해 주세요.");
	        } else {
	            const productSelect = document.getElementById('productNo');
	            productSelect.innerHTML = '';  // 기존 옵션 제거

	            // 상품 목록 추가
	            data.forEach(product => {
	                const option = document.createElement('option');
	                option.value = product.productNo;
	                option.textContent = product.productName;
	                productSelect.appendChild(option);
	            });
	        }
	    })
	    .catch(error => console.error('Error:', error));

});
*/

/**
 *  인콰이어리 작성 내 셀러 검색 ajax
 */
document.addEventListener('DOMContentLoaded', function() {
    const searchSellerBtn = document.getElementById('searchSellerBtn');

    // 버튼이 실제로 존재하는지 확인
    if (searchSellerBtn) {
        searchSellerBtn.addEventListener('click', function() {
            const sellerMemberNo = document.getElementById('sellerMemberNo').value.trim();

            if (sellerMemberNo) {
                // AJAX 요청
                fetch(`/user/${sellerMemberNo}/products`)
                    .then(response => {
                        // 응답이 JSON 형식인지 확인
                        const contentType = response.headers.get("content-type");
                        if (contentType && contentType.indexOf("application/json") !== -1) {
                            return response.json();  // JSON 응답 처리
                        } else {
                            return response.text();  // JSON이 아닌 경우 텍스트로 처리 (에러 응답 처리)
                        }
                    })
                    .then(data => {
                        if (typeof data === 'string') {
                            console.error("Error: Received HTML instead of JSON", data);
                            alert("서버에서 올바른 응답을 받지 못했습니다. 다시 시도해 주세요.");
                        } else {
                            const productSelect = document.getElementById('productNo');
                            productSelect.innerHTML = ''; // 기존 옵션 제거

                            // 상품 목록 추가
                            data.forEach(product => {
                                const option = document.createElement('option');
                                option.value = product.productNo;
                                option.textContent = product.productName;
                                productSelect.appendChild(option);
                            });
                        }
                    })
                    .catch(error => console.error('Error:', error));
            } else {
                alert('셀러 멤버 번호를 입력하세요.');
            }
        });
    } else {
        console.error('searchSellerBtn 버튼을 찾을 수 없습니다.');
    }
});

