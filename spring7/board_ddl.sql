-- 8월 26일 게시판 관련 테이블 생성
-- 회원 전용 게시판
USE dima;

-- 1) 회원 테이블
DROP TABLE dima.boarduser;
CREATE TABLE dima.boarduser
(
	user_id 	varchar(50)
	, user_name varchar(50) NOT NULL
	, user_pwd  varchar(50) NOT NULL
	, email		varchar(100) NOT NULL
	, roles		varchar(20)	 DEFAULT 'ROLE_USER'-- 'ROLE_USER', 'ROLE_ADMIN'
	, enabled   char(1)      DEFAULT '1' 		-- 사용자가 활성화 상태인지 여부를 저장, '1': 활성화, '0': 비활성화
		, CONSTRAINT boarduser_id PRIMARY KEY(user_id)
		, CONSTRAINT boarduser_roles CHECK(roles IN ('ROLE_USER', 'ROLE_ADMIN'))
		, CONSTRAINT boarduser_enabled CHECK(enabled IN ('1', '0'))
);

-- 2) 게시판 테이블
DROP TABLE dima.board;

CREATE TABLE dima.board
(
	board_num		int AUTO_INCREMENT
	, board_writer	varchar(50)  NOT NULL
	, board_title   varchar(200) DEFAULT 'untitled'
	, board_content varchar(3000)
	, hit_count		int DEFAULT 0
	, create_date	datetime DEFAULT current_timestamp
	, update_date	datetime DEFAULT current_timestamp
		, CONSTRAINT board_boardnum PRIMARY KEY(board_num)
);

-- 첨부파일을 위한 컬럼 추가
ALTER TABLE dima.board ADD original_file_name varchar(500); -- 파일의 이름 저장
ALTER TABLE dima.board ADD saved_file_name varchar(500);	-- 변형된 파일명 저장


COMMIT;
SELECT * FROM dima.board;

-- 3) 댓글 테이블 (게시글과 1:다 관계를 형성)
DROP TABLE dima.reply;
CREATE TABLE dima.reply
(
	reply_num int AUTO_INCREMENT
	, board_num int 
	, reply_writer varchar(50) NOT NULL
	, replay_text  varchar(1000) NOT NULL 
	, create_date  datetime DEFAULT current_timestamp
		, CONSTRAINT reply_replynum PRIMARY key(reply_num)
		, CONSTRAINT reply_boardnum FOREIGN key(board_num) REFERENCES dima.board(board_num) ON DELETE CASCADE
);

