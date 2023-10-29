-- SQL 문장 작성 --

CREATE TABLE BOARD(
	IDX INT NOT NULL AUTO_INCREMENT,
	TITLE VARCHAR(100) NOT NULL,
	CONTENT VARCHAR(2000) NOT NULL,
	WRITER VARCHAR(30) NOT NULL,
	INDATE DATETIME DEFAULT NOW(),
	COUNT INT DEFAULT 0,
	PRIMARY KEY(IDX)
);

INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('월요일','캐글관련 공지사항이 있습니다.','AAA');

INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('화요일','오늘도 화이팅 입니다','BBB');

INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('수요일','최종프로젝트 팀 결정','CCC');

INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('목요일','내일은 금요일','DDD');

INSERT INTO BOARD(TITLE, CONTENT, WRITER)
VALUES('금요일','점심에 항상 상추쌈 나오는 날','EEE');

SELECT * FROM BOARD;

DROP TABLE BOARD;




