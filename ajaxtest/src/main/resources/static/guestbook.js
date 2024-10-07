/**
 * 방명록 관련 ajax 코드
 */

$(function(){
	init();
	$('#save').on('click', input);
});

// 전체 데이터를 다시 로딩
function init() {
	$.ajax({
		url: 'retrieveAll'
		, method: 'GET'
		, success : output
	})
}

// 로딩된 데이터를 출력함수
function output(resp) { 
	let tag = '';
	$.each(resp, function(index, item){
		// tbody에 삽입될 tag를 만드는 코드
		tag += `
			<tr>
		      	<td class="idx">${(index+1)}</td>
		        <td class="name">${item["name"]}</td>
		        <td class="content">${item["content"]}</td>
		        <td class="date">${item["createDate"]}</td>
		        <td class="btn"><input type="button" class="btn" data-seq="${item["seq"]}" value="삭제" </td>
			</tr>
			`;
		});
		$('tbody').html(tag);
		$('.btn').on('click', deleteItem);
}

// 데이터를 삭제하는 함수
function deleteItem(e) {
	let seq = $(this).attr("data-seq");
	let pwd = prompt("비밀번호를 입력하세요");
	console.log(seq);
	
	let answer = confirm("삭제하시겠습니까?");
	e.stopPropagation();	// 창이 두 번 뜨지 않도록!

	if(!answer) return;
	
	$.ajax({
		url : 'deleteGuestbook'
		, method: 'POST'
		, data : {"seq" : seq, "pwd": pwd}
		, success : init
	});
}

// 방명록 데이터를 수집해서 ajax를 기반으로 DB에 저장
function input() {
	let name = $('#name').val();
	let pwd  = $('#pwd').val();
	let content = $("#content").val();
	
	let sendData = {"name" : name, "pwd" : pwd, "content": content};
	
	$.ajax({
		url: 'inputGuestbook'
		, method: 'POST'
		, data : sendData
		, success  : function() {
			init();
			clearAll();
		}
	});
}

// 입력상자의 데이터를 삭제
function clearAll(){
	$('#name').val("");
	$('#pwd').val("");
	$("#content").val("");
}




