-- 1008_인콰이어리 리스트 관련 테이블 생성
-- inq 리스트 
USE tkforest;

-- 인콰이어리 테이블
CREATE TABLE INQUIRY (
    INQUIRYNO INT NOT NULL AUTO_INCREMENT PRIMARY KEY,           -- 인콰이어리 고유번호 (Primary Key, 자동 증가)
    PRODUCTNO INT NOT NULL,                                      -- 상품 고유번호 (Foreign Key)
    BUYER_MEMBERNO VARCHAR(10) NOT NULL,                         -- 발송자(바이어) 회원 고유번호
    SELLER_MEMBERNO VARCHAR(10) NOT NULL,                        -- 수신자(셀러) 회원 고유번호
    SUBJECT VARCHAR(300) DEFAULT 'No Title',                     -- 인콰이어리 제목 (NULL 허용, 기본값: 'No Title')
    CONTENTS LONGTEXT,                     -- 인콰이어리 내용 (NULL 허용, 기본값: 'No Contents')
    OFFERSENDDATE DATETIME DEFAULT current_timestamp,             -- 인콰이어리 발송일
    OFFEREXPIREDATE DATETIME DEFAULT NULL,                       -- 인콰이어리 유효기간 종료날짜 (NULL 허용)
    ORDERQUANTITY INT DEFAULT 0,                                 -- 주문수량 (NULL 허용, 기본값: 0)
    ORDERUNITETC VARCHAR(100) DEFAULT NULL,                      -- 주문단위 기타 (NULL 허용)
    EXPECTEDPRICE DECIMAL(10, 2) DEFAULT 0.00,                   -- 희망가격 (NULL 허용, 기본값: 0.00)
	ORIGINAL_FILE_NAME VARCHAR(500),							 -- 파일의 이름 저장
	SAVED_FILE_NAME VARCHAR(500),								 -- 변형된 파일명 저장
		FOREIGN KEY (PRODUCTNO) REFERENCES PRODUCT(PRODUCTNO),        -- PRODUCTNO는 PRODUCT 테이블을 참조하는 외래 키
		FOREIGN KEY (BUYER_MEMBERNO) REFERENCES BUYER(BUYER_MEMBERNO),
		FOREIGN KEY (SELLER_MEMBERNO) REFERENCES SELLER(SELLER_MEMBERNO)
);

-- 인콰이어리 답변 테이블
CREATE TABLE INQUIRY_REPLY (
    REPLYNO INT NOT NULL AUTO_INCREMENT PRIMARY KEY,              -- 인콰이어리 답변 고유번호 (Primary Key, 자동 증가)
    INQUIRYNO INT NOT NULL,                                       -- 인콰이어리 고유번호 (Foreign Key)
    REPLY_PARENT INT DEFAULT NULL,                                -- 상위 답변 고유번호 (NULL 허용)
    REPLY_WRITER_TYPE ENUM('BUYER', 'SELLER') NOT NULL,           -- 답변 작성자 유형 (바이어 또는 셀러)
    REPLY_CONTENTS LONGTEXT NOT NULL,                             -- 답변 내용 (NULL 허용 안 함)
    REPLY_DATE DATETIME NOT NULL DEFAULT current_timestamp,       -- 답변 작성일 (현재 시간 기본값)
    REPLY_WRITER VARCHAR(10) NOT NULL,                            -- 답변 작성자 (VARCHAR 타입, NOT NULL)
		FOREIGN KEY (INQUIRYNO) REFERENCES INQUIRY(INQUIRYNO)         -- INQUIRY 테이블의 INQUIRYNO를 참조하는 외래 키
);