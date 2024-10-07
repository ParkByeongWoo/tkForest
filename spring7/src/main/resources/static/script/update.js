/**
 * 회원정보 수정을 위해 검증
 */

let pwdCheck = false; 

$(function(){
	$('#userPwd').on('focus', function() {
		$('#userPwdCheck').val('');
	});
	
	$('#submitBtn').on('click', update);
});

// 비밀번호, 비밀번호확인, 이름이 입력되었는지 확인 ==> idCheck, pwdCheck 값이 true일 경우에만 가입
function update() {
	let userPwd    = $("#userPwd").val();
	if(userPwd.trim().length < 3 ||  userPwd.trim().length > 5) {
		$('#confirmPwd').css('color', 'red');
		$('#confirmPwd').html('비밀번호는 3~5자 사이로 입력하시오!');
		pwdCheck = false;
		return;
	}
	
	let userPwdCheck = $("#userPwdCheck").val();
	
	if(userPwd.trim() != userPwdCheck.trim()) {
		$('#confirmPwd').css('color', 'red');
		$('#confirmPwd').html('비밀번호와 비밀번호 확인은 값이 같아야 합니다.');
		pwdCheck = false;
		return;
	}
	
	$('#confirmPwd').html('');
	pwdCheck = true;
	
	// 이메일 체크
	let email = $('#email').val();
	if(email.trim().length == 0) {
		$('#emailCheck').css('color', 'red');
		$('#emailCheck').html('이메일을 입력하세요');
		$('#email').select();
		return;
	} 
	$('#emailCheck').html('');	
	
	// 수정 진행 전에 확인
	if(!pwdCheck) {
		alert('모든 정보를 정확히 입력해 주세요');
		return ;
	}
	$('#updateForm').submit();
}
