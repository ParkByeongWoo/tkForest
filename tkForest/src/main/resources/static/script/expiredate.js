/**
 * 인콰이어리 만료일 계산 AJAX
 */


document.addEventListener('DOMContentLoaded', function () {
    const offerSendDateElement = document.getElementById('offerSendDate');
    const expirationOptionElement = document.getElementById('expirationOption');
    const offerExpireDateElement = document.getElementById('offerExpireDate');
    const submitButtonElement = document.getElementById('submitButton');

    if (offerSendDateElement && expirationOptionElement) {
        offerSendDateElement.addEventListener('change', calculateExpirationDate);
        expirationOptionElement.addEventListener('change', calculateExpirationDate);
    }

    if (submitButtonElement) {
        submitButtonElement.addEventListener('click', function() {
            const sendDate = offerSendDateElement.value;
            const expireDate = offerExpireDateElement.value;
            const expirationOption = expirationOptionElement.value;

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
    }

    function calculateExpirationDate() {
        const sendDateInput = offerSendDateElement.value;
        const expirationOption = expirationOptionElement.value;

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
            offerExpireDateElement.value = formattedDate;
        }
    }
});
