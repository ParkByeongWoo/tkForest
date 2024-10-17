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
	$('#confirmSellerId').on('click', confirmSellerId);
	$('#confirmBuyerId').on('click', confirmBuyerId);
	
	// 국가 목록 가져오기
	    fetch('https://restcountries.com/v3.1/all')
	        .then(response => response.json())
	        .then(data => {
	            const countries = data.map(country => ({
	                name: country.name.common,
	                code: country.cca2 // 또는 country.cca3
	            }));
	            // 드롭다운 목록에 국가 추가
	            const countrySelect = $('#country'); // 드롭다운의 ID
	            countries.forEach(country => {
	                const option = $('<option></option>')
	                    .attr('value', country.code) // 국가 코드
	                    .text(country.name); // 국가 이름
	                countrySelect.append(option);
	            });
	        })
	        .catch(error => console.error('Error fetching countries:', error));

	// 가입 버튼 클릭 시 확인 (시간상 일단 pass)
	// $('#submitBtn').on('click', join);		
		
		
});


// (셀러) 사용가능한 아이디인지 여부를 판단(ajax로 작업);
function confirmSellerId(){
	
	let userId = $('[data-role="userId"]').val();

	/*	
	if (userId.trim().length < 3 || userId.trim().length > 5) {
		$('#confirmId').css('color', 'red');
		$('#confirmId').html('id는 3~5자 사이로 입력하세요❗');
		return;
	}
	*/
	
	// 아이디 공백인지 체크
	if (userId.trim().length == 0) {
		$('#confirmId').css('color', 'red');
		$('#confirmId').html('입력된 id가 없습니다.');
		return;
	}
	
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

// (바이어) 사용가능한 아이디인지 여부를 판단(ajax로 작업);
function confirmBuyerId(){
	
	let userId = $('[data-role="userId"]').val();

	/*	
	if (userId.trim().length < 3 || userId.trim().length > 5) {
		$('#confirmId').css('color', 'red');
		$('#confirmId').html('id는 3~5자 사이로 입력하세요❗');
		return;
	}
	*/
	
	// 아이디 공백인지 체크
	if (userId.trim().length == 0) {
		$('#confirmId').css('color', 'red');
		$('#confirmId').html('Please enter id.');
		return;
	}
	
	// 중복 아이디인지 체크
	$.ajax({
		url: "/user/confirmId"
		, method: "POST"
		, data: {"userId":userId}
		, success: function(resp) {	// resp = true이면 사용가능한 아이디
			if (resp) {
				$('#idCheck').css('color', 'blue');
				$('#idCheck').html('This is an available ID.');
				// $('#idCheck').html('');
				idCheck = true;
			} else {
				$('#idCheck').css('color', 'red');
				$('#idCheck').html('This ID is already in use.');
				idCheck = false;
			}
		}
	});
	
	// $('#confirmId').html('');
	
}

/*
function join() {
	
	// 비밀번호 = 비밀번호확인 체크
	let userPwd = $('#userPwd').val();
	let userPwdCheck = $('#passwordCheck').val();

	if (userPwd.trim() != userPwdCheck.trim()) {
		$('#confirmPwd').css('color', 'red');
		$('#confirmPwd').html('비밀번호가 같지 않습니다.');
		pwdCheck = false;
		return;
	}
	$('#confirmPwd').html('');
	pwdCheck = true;
	
	
	// 가입
	if (!idCheck) {
		alert('아이디를 정확히 입력해 주세요');
		return;
	} 
		
	if (!pwdCheck) {
		alert('비밀번호를 정확히 입력해 주세요');
		return;
	}
	
	$('#joinForm').submit();
	
}
*/


