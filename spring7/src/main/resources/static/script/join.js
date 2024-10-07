/**
 * 회원 가입할 때 Validation 제어
 */
/**
 * 전역변수 : 아이디, 비번이 옳바를 경우에만 가입가능
 */
let idCheck = false;   // false의 경우 가입 불가능!
let pwdCheck = false; 

$(function(){
	$('#userId').on('keyup', confirm);
	$('#userId').on('blur', function() {
		$('#confirmId').html('');	
	});
	$('#userPwd').on('focus', function() {
		$('#userPwdCheck').val('');
	});
	$('#submitBtn').on('click', join);
});

// 비밀번호, 비밀번호확인, 이름이 입력되었는지 확인 ==> idCheck, pwdCheck 값이 true일 경우에만 가입
function join() {
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
	
	// 사용자 이름체크
	let userName = $('#userName').val();
	if(userName.trim().length == 0 ) {
		$('#userNameCheck').css('color', 'red');
		$('#userNameCheck').html('실명의 이름을 입력하세요');
		$('#userName').select();
		return;
	}
	$('#userNameCheck').html('');
	
	// 이메일 체크
	let email = $('#email').val();
	if(email.trim().length == 0) {
		$('#emailCheck').css('color', 'red');
		$('#emailCheck').html('이메일을 입력하세요');
		$('#email').select();
		return;
	} 
	$('#emailCheck').html('');	
	
	// 가입
	if(!idCheck || !pwdCheck) {
		alert('모든 정보를 정확히 입력해 주세요');
		return ;
	}
	$('#joinForm').submit();
}

// 사용가능한 아이디인지 여부를 판단(ajax로 작업);
function confirm() {
	let userId = $('#userId').val();
	
	if(userId.trim().length < 3 ||  userId.trim().length > 5) {
		$('#confirmId').css('color', 'red');
		$('#confirmId').html('길이는 3~5자 사이로 입력하시오!');
		return;
	}
	
	// 중복 아이디인지 체크
	$.ajax({
		url: "/user/confirmId"
		, method: "POST"
		, data : {"userId": userId}
		, success : function(resp) { // resp가 true일때 가입 가능
			if(resp) {
				$('#confirmId').css('color', 'blue');
				$('#confirmId').html('사용 가능한 아이디입니다.');
				idCheck = true;
			} else {
				$('#confirmId').css('color', 'red');
				$('#confirmId').html('사용 불가능한 아이디입니다.');
				idCheck = false;
			}
		}
	});
}



