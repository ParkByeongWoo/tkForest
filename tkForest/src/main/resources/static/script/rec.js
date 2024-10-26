/**
 * 댓글 관련 Ajax 코드
 */

$(function() {
	$('#recBtn').on('click', recList);
});

// 모든 댓글 목록(게시글의 모든 댓글)을 읽어옴 
function recList() {
	let buyerMemberNo = $("#buyerMemberNo").val();   // '${board.boardNum}'
	
	$.ajax({
		url: '/rec/recList'
		, method: 'POST'
		, data : {"buyerMemberNo":buyerMemberNo }
		, success : output
	})
}
/**
 * function output(resp) {
	if(resp.length==0) return;
	$.each(resp, function(index, item){
		let tag =`
		 <div class="product-grid">
		   <th:block th:each="product : ${item}">
		      <!-- product-card를 클릭하면 상세페이지로 이동 -->
		         <div class="product-card">
		      <a th:href="@{/product/productDetail/{productNo}(productNo=${product.productNo})}" class="product-card-link">
		            <div class="product-image">
		               <!-- 이미지가 없을 경우 텍스트로 대체 -->
		               <img th:src="@{/images/products/ + ${product.image}}" alt="Product Image"
		                  onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';" />
		               <div class="image-placeholder" style="display: none; align-items: center; justify-content: center; height: 250px; background-color: #e0e0e0; color: #333; text-align: center;">
		                  <span>800 X 700</span> <!-- 이미지가 없을 때 나타날 대체 텍스트 -->
		               </div>
		            </div>
		            <div class="product-details">
		               <h3 th:text="${product.productName}" style="font-size: 0.9rem; font-weight: bold; margin-top:0.5rem;">Product Name</h3>
		               <p  th:text="${product.sellerMemberNo}">Seller Name</p>
		               <p th:text="${product.registrationDate}">Registration Date</p>
		               <p>
		              <a th:href="@{/product/productList(query=${product.brand}, searchType='Brand')}">
		                <span th:text="${product.brand}">Brand</span>
		              </a>
		            </p>
		            </div>
		            <div class="product-actions">
		               <button class="like-button">❤️</button>
		               <button class="contact-button">✉️</button>
		            </div>
		         </div>
		      </a>
		   </th:block>
		</div>
`
	});
	$('#product-list').html(tag);
}
*/
function output(resp) {
    if (resp.length === 0) return; // 응답이 없으면 종료

    let tags = ''; // HTML 태그를 저장할 변수 초기화
    $.each(resp, function(index, product) {
        // 각 product에 대해 HTML 태그 생성
        tags += `
        <div class="product-card">
            <a th:href="@{/product/productDetail/{productNo}(productNo=${product.productNo})}" class="product-card-link">
                <div class="product-image">
                    <img th:src="@{/images/products/${product.image}}" alt="Product Image"
                         onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';" />
                    <div class="image-placeholder" style="display: none; align-items: center; justify-content: center; height: 250px; background-color: #e0e0e0; color: #333; text-align: center;">
                        <span>800 X 700</span>
                    </div>
                </div>
                <div class="product-details">
                    <h3 th:text="${product.productName}" style="font-size: 0.9rem; font-weight: bold; margin-top:0.5rem;">${product.productName}</h3>
                    <p th:text="${product.sellerMemberNo}">${product.sellerMemberNo}</p>
                    <p th:text="${product.registrationDate}">${product.registrationDate}</p>
                    <p>
                        <a th:href="@{/product/productList(query=${product.brand}, searchType='Brand')}">
                            <span th:text="${product.brand}">${product.brand}</span>
                        </a>
                    </p>
                </div>
                <div class="product-actions">
                    <button class="like-button">❤️</button>
                    <button class="contact-button">✉️</button>
                </div>
            </a>
        </div>
        `;
    });

    // 생성된 HTML을 #product-list에 삽입
    $('#product-list').html(tags);
}

