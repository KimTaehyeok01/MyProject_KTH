-- 파일이름 : db.sql

-- db 생성
DROP DATABASE IF EXISTS calc;
CREATE DATABASE calc;

-- db 사용 설정
USE calc;

-- 회원정보 테이블
DROP TABLE if EXISTS calc.history;
CREATE TABLE calc.history(
   history_no INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 고유키
	op VARCHAR(255) NOT NULL, -- 연산자 add, sub, mul, div
   input1 INT NOT NULL, -- 입력값1
   input2 INT NOT NULL, -- 입력값2
   result INT NOT NULL -- 연산결과
);

-- 테이블 구조 확인
DESCRIBE calc.history;

-- 데이터 추가
insert INTO calc.history values ( 0, 'add', 1, 2, 3);

-- 데이터 조회
SELECT * FROM calc.history;

-- 데이터 수정
UPDATE calc.history SET result = 4 WHERE histroy_no = 1;

-- 데이터 조회
SELECT * FROM calc.history;