/**
 * 인콰이어리 만료일 계산 AJAX
 

document.getElementById('offerSendDate').addEventListener('change', calculateExpirationDate);
document.getElementById('expirationOption').addEventListener('change', calculateExpirationDate);

function calculateExpirationDate() {
    const sendDateInput = document.getElementById('offerSendDate').value;
    const expirationOption = document.getElementById('expirationOption').value;

    if (sendDateInput) {
        const sendDate = new Date(sendDateInput);
        const expirationDate = new Date(sendDate);

        expirationDate.setMonth(expirationDate.getMonth() + parseInt(expirationOption));

        // datetime-local 형식으로 날짜 포맷팅
        const formattedDate = expirationDate.toISOString().slice(0, 16); // YYYY-MM-DDTHH:MM
        document.getElementById('offerExpireDate').value = formattedDate;
    }
}

document.getElementById('submitButton').addEventListener('click', function() {
    const sendDate = document.getElementById('offerSendDate').value;
    const expireDate = document.getElementById('offerExpireDate').value;
    const expirationOption = document.getElementById('expirationOption').value;

    // 서버에 AJAX로 데이터 전송
    const xhr = new XMLHttpRequest();
    xhr.open("POST", "/saveOffer", true); // '/saveOffer'는 데이터를 처리할 서버의 URL
    xhr.setRequestHeader("Content-Type", "application/json");

    const data = JSON.stringify({
        offerSendDate: sendDate,
        offerExpireDate: expireDate,
        expirationOption: expirationOption
    });

    xhr.onreadystatechange = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert('Offer successfully submitted.');
            // 필요한 추가 처리
        } else if (xhr.readyState === 4) {
            alert('Failed to submit offer.');
        }
    };

    xhr.send(data);
});
*/

document.addEventListener('DOMContentLoaded', function () {
    // 발송일 또는 만료 기간 선택 시 만료일 자동 계산
    document.getElementById('offerSendDate').addEventListener('change', calculateExpirationDate);
    document.getElementById('expirationOption').addEventListener('change', calculateExpirationDate);

    function calculateExpirationDate() {
        const sendDateInput = document.getElementById('offerSendDate').value;
        const expirationOption = document.getElementById('expirationOption').value;

        console.log("sendDateInput: ", sendDateInput);
        console.log("expirationOption: ", expirationOption);

        // 발송일과 만료 기간 옵션이 모두 선택되어 있을 때만 만료일 계산
        if (sendDateInput && expirationOption) {
            const sendDate = new Date(sendDateInput);
            const expirationDate = new Date(sendDate);

            // 옵션 값(1, 6, 12, 24)에 따라 개월 수를 더함
            const monthsToAdd = parseInt(expirationOption);
            expirationDate.setMonth(expirationDate.getMonth() + monthsToAdd);

            // datetime-local 형식으로 날짜 포맷팅
            const formattedDate = expirationDate.toISOString().slice(0, 16); // YYYY-MM-DDTHH:MM 형식으로 변환
            document.getElementById('offerExpireDate').value = formattedDate;
			
			// 오류 시 로그 확인
            //console.log("Calculated expiration date: ", formattedDate);
        }
    }
});
