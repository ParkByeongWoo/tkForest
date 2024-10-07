/**
 * 
 */
$(function(){
	$('#predictBtn').on('click', irisPredict);
});

function irisPredict() {
	let petalLength = $('#petalLength').val();
	let petalWidth = $('#petalWidth').val();
	let sepalLength = $('#sepalLength').val();
	let sepalWidth = $('#sepalWidth').val();
	
	// 문자열이 입력되지 않도록 체크
	if(isNaN(petalLength) || isNaN(petalWidth) 
		|| isNaN(sepalLength) || isNaN(sepalWidth)) {
			alert("데이터는 숫자로 입력해 주세요");
			return ;
	}
	
	let sendData = {"petalLength" : petalLength
					, "petalWidth": petalWidth
					, "sepalLength" : sepalLength
					, "sepalWidth": sepalWidth };
					
	$.ajax({
		url: 'predict'
		, method : 'POST'
		, async : false
		, data : sendData
		, success : function(resp) {  // resp = {"predict_result" : "setosa"}
			alert(JSON.stringify(resp))
			$('#result').text(resp["predict_result"]);
		}
		, error : function(resp) {
			alert("err : " + JSON.stringify(resp));
		} 
	})
	
	
	
	
					
}