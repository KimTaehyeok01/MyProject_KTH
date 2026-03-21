-- 파일이름 : db.sql

-- db 생성
DROP DATABASE IF EXISTS vm2;
CREATE DATABASE vm2;

-- db 사용 설정
USE vm2;

-- 회원정보 테이블
DROP TABLE if EXISTS vm2.product;

CREATE TABLE vm2.product(
   product_no INT AUTO_INCREMENT NOT NULL PRIMARY KEY, -- 고유키
   product_name VARCHAR(255) NOT NULL, -- 상품이름
   product_price INT NOT NULL, -- 상품가격
   product_limit_date DATETime DEFAULT NOW() -- 유통가능일자
);

-- 테이블 구조 확인
DESCRIBE vm2.product;
SELECT *FROM product;