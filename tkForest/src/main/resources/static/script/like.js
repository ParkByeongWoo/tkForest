/**
 * 상품에 like 버튼 누르면 B_LIKE에 저장되도록
 */


$(function() {
	// $('#like-button').on('click', productLike);
	
	console.log("jQuery is loaded");  // jQuery가 로드된 후 이 메시지가 뜨는지 확인
	
	// 부모 요소에 이벤트 위임 방식 사용
	$(document).on('click', '.like-button', productLike);
	
	
});		

function productLike() {
	alert("좋아요 버튼 클릭함");
	
    // 버튼에 있는 data-productno 속성에서 productNo 값을 가져옴
    let productNo = $(this).data('productno');
	let buyerMemberNo = $('#buyerMemberNo').val();

	alert(productNo);
	alert(buyerMemberNo);
	
    // Ajax 요청을 통해 좋아요 서비스 호출
    $.ajax({
        url: '/product/productLike',  // 좋아요 처리하는 서비스의 URL
        method: 'POST',
        data: {
			buyerMemberNo: buyerMemberNo,
            productNo: productNo,  // 상품 번호 전송
            likeUseYn: 'Y'  // 좋아요 상태 전송
        },
        success: function(response) {
            // 성공적으로 처리되면 알림 메시지
            alert('좋아요가 처리되었습니다.');
        },
        error: function(xhr, status, error) {
            // 오류가 발생했을 때 오류 메시지 표시
            alert('좋아요 처리 중 오류가 발생했습니다. 다시 시도해주세요.');
        }
    });
}