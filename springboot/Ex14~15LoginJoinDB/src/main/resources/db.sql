-- 파일이름 : db.sql

-- db 생성
DROP DATABASE IF EXISTS loginjoin;
CREATE DATABASE loginjoin;

-- db 사용 설정
USE loginjoin;

-- 회원정보 테이블
DROP TABLE if EXISTS loginjoin.member;
CREATE TABLE loginjoin.member(
   member_no INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 고유키
   member_username VARCHAR(255) NOT NULL, -- 아이디
   member_password VARCHAR(255) NOT NULL, -- 암호
   member_email VARCHAR(255), -- 이메일
   member_joindate DATE NOT NULL,   -- 가입일
   member_role VARCHAR(20)  -- 권한
);
-- 기존 테이블에 권한 컬럼 추가하기
ALTER TABLE member ADD COLUMN member_role VARCHAR(20) NOT NULL DEFAULT 'ROLE_USER';
-- 테이블 구조 확인
DESCRIBE loginjoin.member;

SELECT *FROM MEMBER;

INSERT INTO MEMBER values(0, "taehyeok", "123456", "taehyeok1124@gmail.com", "2023-12-25", "ROLE_ADMIN");

SELECT * FROM member WHERE member_username = 'taehyeok';
