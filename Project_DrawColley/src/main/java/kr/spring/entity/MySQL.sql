CREATE TABLE TBLBOARD(
	IDX INT NOT NULL,
	MEMID VARCHAR(20) NOT NULL,
	TITLE VARCHAR(100) NOT NULL,
	CONTENT VARCHAR(2000) NOT NULL,
	WRITER VARCHAR(30) NOT NULL,
	INDATE DATETIME DEFAULT NOW(),
	COUNT INT DEFAULT 0,
	-- 댓글 기능 추가 --
	BOARDGROUP INT, -- 부모 댓글 하나의 그룸 -- 
	BOARDSEQUENCE INT, -- 같은 그룹안에서 댓글의 순서 -- 
	BOARDLEVEL INT, -- 1단계 댓글인지 2단계 댓글인지에 대한 정보 --
	BOARDAVAILABLE INT, -- 삭제된 글인지 여부 --
	PRIMARY KEY(IDX)
);

INSERT INTO TBLBOARD
SELECT IFNULL(MAX(IDX + 1), 1),
'aischool', '공지사항입니다.','월요일 인공지능 사관학교 정상 운영합니다.','교육운영부',
NOW(), 0, IFNULL(MAX(BOARDGROUP +1), 1), 0, 0, 1
FROM TBLBOARD;

INSERT INTO TBLBOARD
SELECT IFNULL(MAX(IDX + 1), 1),
'PBK', 'Spring반 공지','간식추천받습니다.','박병관',
NOW(), 0, IFNULL(MAX(BOARDGROUP +1), 1), 0, 0, 1
FROM TBLBOARD;

INSERT INTO TBLBOARD
SELECT IFNULL(MAX(IDX + 1), 1),
'JJY', '출결공지','9시 까지 등원바랍니다.','조준용',
NOW(), 0, IFNULL(MAX(BOARDGROUP +1), 1), 0, 0, 1
FROM TBLBOARD;

SELECT * FROM TBLBOARD;

DROP TABLE TBLBOARD

CREATE TABLE TBLMEMBER(
	MEMID VARCHAR(50) NOT NULL,
	MEMPWD VARCHAR(50) NOT NULL,
	MEMNAME VARCHAR(50) NOT NULL,
	MEMPHONE VARCHAR(50) NOT NULL,
	MEMADDR VARCHAR(100),
	LATITUDE DECIMAL(13,10), -- 현재위치 위도 --
	LONGITUDE DECIMAL(13, 10), -- 현재위치 경도 --
	PRIMARY KEY(MEMID)
);

INSERT INTO TBLMEMBER(MEMID, MEMPWD, MEMNAME, MEMPHONE)
VALUES('aischool','1234','교육운영부','010-1111-1111');

INSERT INTO TBLMEMBER(MEMID, MEMPWD, MEMNAME, MEMPHONE)
VALUES('PBK','1234','박병관','010-2222-2222');

INSERT INTO TBLMEMBER(MEMID, MEMPWD, MEMNAME, MEMPHONE)
VALUES('JJY','1234','조준용','010-3333-3333');

SELECT * FROM TBLMEMBER;







