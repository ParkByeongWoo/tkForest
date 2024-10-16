/**
 * 회원 가입할 때 Validation 제어
 */

/**
 * 전역변수 : 아이디, 비밀번호가 올바른 경우에만 가입가능
 */
let idCheck = false;	// 일단 기본은 false(가입 불가능)
let pwdCheck = false;

$(function(){
	// 아이디 확인 버튼 클릭 -> 중복 아이디 체크	
	$('#confirmId').on('click', confirmId);

});


// 사용가능한 아이디인지 여부를 판단(ajax로 작업);
function confirmId(){
	
	let userId = $('#sellerId').val();

	/*	
	if (userId.trim().length < 3 || userId.trim().length > 5) {
		$('#confirmId').css('color', 'red');
		$('#confirmId').html('id는 3~5자 사이로 입력하세요❗');
		return;
	}
	*/
	
	// 중복 아이디인지 체크
	$.ajax({
		url: "/user/confirmId"
		, method: "POST"
		, data: {"userId":userId}
		, success: function(resp) {	// resp = true이면 사용가능한 아이디
			if (resp) {
				$('#idCheck').css('color', 'blue');
				$('#idCheck').html('사용가능한 아이디입니다.');
				// $('#idCheck').html('');
				idCheck = true;
			} else {
				$('#idCheck').css('color', 'red');
				$('#idCheck').html('이미 사용중인 아이디입니다.');
				idCheck = false;
			}
		}
	});
	
	// $('#confirmId').html('');
	
}