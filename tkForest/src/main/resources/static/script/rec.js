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

function output(resp) {
    if (resp.length === 0) return; // 응답이 없으면 종료
/**
 * 
    let tags = ''; // HTML 태그를 저장할 변수 초기화
	let chk=0
    $.each(resp, function(index, product) {
        // 각 product에 대해 HTML 태그 생성
		chk+=1
		if (chk>5){
			tag+=``
		} 
        tags += `
		<article class="component-8">
		                    <div class="link-1">
		                      <div class="_1jpg"></div>
		                      <div class="border-1">
		                        <div class="background-1"></div>
		                      </div>
		                    </div>
		                    <div class="background-2">
		                      <div class="heading-5margin heading">
		                        <div class="component-7">
		                          <div class="text-2 valign-text-middle x127001poppinsregular-13-title">
		                            <h3 th:text="${product.productName}" style="font-size: 0.9rem; font-weight: bold; margin-top:0.5rem;">${product.productName}</h3>
		                          </div>
		                        </div>
		                      </div>
		                      <div class="margin-1 margin-6">
		                        <div class="container-49">
		                          <div class="margin-2 margin-6">
		                            <div class="container-7">
		                              <div class="container-8">
									  <a th:href="@{/product/productList(query=${product.brand}, searchType='Brand')}">
									                              <span th:text="${product.brand}">${product.brand}</span>
									                          </a>
		                              </div>
		                            </div>
		                          </div>
		                          <div class="container-10">
		                            <div class="margin-3 margin-6">
		                              <div class="price valign-text-middle price-2 x127001poppinsbold-14">
									                      <p th:text="${product.registrationDate}">${product.registrationDate}</p>
														  </div>
		                            </div>

		                          </div>
		                        </div>
		                      </div>
		                    </div>
		                  </article>
        `;
    });
 */
	let chk = 0;
	let tags = '';
	tags += `<div class="product-container">`; // 컨테이너 시작

	$.each(resp, function(index, product) {
	    chk += 1;
	    if (chk > 10) return false; // 최대 10개까지만 표시

	    // 각 product에 대해 HTML 태그 생성
	    tags += `
	    <article class="component-8">
	        <div class="link-1">
	            <div class="_1jpg"></div>
	            <div class="border-1">
	                <div class="background-1"></div>
	            </div>
	        </div>
	        <div class="background-2">
	            <div class="heading-5margin heading">
	                <div class="component-7">
	                    <div class="text-2 valign-text-middle x127001poppinsregular-13-title">
	                        <h3 th:text="${product.productName}" style="font-size: 0.9rem; font-weight: bold; margin-top:0.5rem;">
	                            ${product.productName}
	                        </h3>
	                    </div>
	                </div>
	            </div>
	            <div class="margin-1 margin-6">
	                <div class="container-49">
	                    <div class="margin-2 margin-6">
	                        <div class="container-7">
	                            <div class="container-8">
	                                <a th:href="@{/product/productList(query=${product.brand}, searchType='Brand')}">
	                                    <span th:text="${product.brand}">${product.brand}</span>
	                                </a>
	                            </div>
	                        </div>
	                    </div>
	                    <div class="container-10">
	                        <div class="margin-3 margin-6">
	                            <div class="price valign-text-middle price-2 x127001poppinsbold-14">
	                                <p th:text="${product.registrationDate}">${product.registrationDate}</p>
	                            </div>
	                        </div>
	                    </div>
	                </div>
	            </div>
	        </div>
	    </article>
	    `;
	});

	tags += `</div>`; // 컨테이너 끝	
	

    // 생성된 HTML을 #product-list에 삽입
    $('#product-list').html(tags);
}


/**
 * 
 * `
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
        `
 */
