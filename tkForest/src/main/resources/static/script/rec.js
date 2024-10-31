
$(function () {
	$('#recBtn').on('click', recList);
});

function recList() {
	let buyerMemberNo = $("#buyerMemberNo").val();   // '${board.boardNum}'

	// 로딩 스피너 보이기
	$('#loading-spinner').removeClass('hidden');

	$.ajax({
		url: '/rec/recList'
		, method: 'POST'
		, data: { "buyerMemberNo": buyerMemberNo }
		, success: function (resp) {
			output(resp);
		},
		error: function () {
			alert('Connecting Error');
		},
		complete: function () {
			// 로딩 스피너 숨기기
			$('#loading-spinner').addClass('hidden');
		}
	})
}

function output(resp) {
	if (resp.length === 0) return; // 응답이 없으면 종료
	let chk = 0;
	let tags = '';
	tags += `<div class="product-container">`; // 컨테이너 시작

	$.each(resp, function (index, product) {
		chk += 1;
		if (chk > 10) return false; // 최대 10개까지만 표시

		// 각 product에 대해 HTML 태그 생성
		tags += `
				<article class="component-8">
					<div class="link-1">
					<div>
					<a href="/product/productDetail?productNo=${product.productNo}&searchType=null&query=null" class="product-card-link">
						<div class="_1jpg">
							<img src="/uploadimage/${product.productNo}.jpg" alt="Product Image" class="product-image"onerror="this.style.display='none'; this.nextElementSibling.style.display='flex';" style="width:238.4px; height:208.6px; object-fit: contain; background-color:#ffffff;">
						</div>
						</a>
						</div>
						<div class="background-2">
							<div class="heading-5margin heading">
								<div class="component-7">
									<div class="text-2 valign-text-middle x127001poppinsregular-13-title">
									<a href="/product/productDetail?productNo=${product.productNo}&searchType=null&query=null" class="product-card-link">	
									<h3 text="${product.productName}" style="font-size: 0.9rem; font-weight: bold; margin-top:0.5rem;">
											${product.productName}
										</h3>
										</a>
									</div>
								</div>
							</div>
							<div class="margin-1 margin-6">
								<div class="container-49">
									<div class="margin-2 margin-6">
										<div class="container-7">
											<div class="container-8">
													<a href="/product/productSellerStore/${product.sellerMemberNo}" >
													<span text="${product.companyName}">${product.companyName}</span>
											</a>
													</div>
										</div>
									</div>
									<div class="container-10">
										<div class="margin-3 margin-6">
											<div class="price valign-text-middle price-2 x127001poppinsbold-14">
											<p text="${product.registrationDate}">${product.registrationDate}</p>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
				</article>`
			;
	});

	tags += `</div>`; // 컨테이너 끝	

	let tag2 = `<img class="vector-1" src="img/vector-1.svg" alt="Vector">
		   	<a href="/product/productList" class="see-more-link">See More</a>`
	// 생성된 HTML을 #product-list에 삽입
	$('#product-list').html(tags);
	$('#see-more-container').html(tag2);
}
