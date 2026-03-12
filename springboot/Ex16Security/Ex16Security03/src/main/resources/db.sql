USE mydb;

-- 회원가입 테이블 만들기
DROP TABLE member_security;

CREATE TABLE member_security(
	id Bigint PRIMARY KEY NOT NULL AUTO_INCREMENT,
	username varchar(255) NOT NULL, -- 아이디
	password varchar(255) NOT NULL, -- 비밀번호
	nick_name varchar(255) NOT NULL,
	user_role varchar(255) DEFAULT 'ROLE_USER',
	join_date date DEFAULT (CURRENT_DATE)
);

-- 암호는 BCrypt 암호화 하여 저장한다.
-- bcrypt-generator.com 강도 12로 '1234'로 만들기
INSERT INTO member_security values(0,'hong','$2a$12$0ygh9yEtqMZ6q7pyoQ4gPOOHOSR99ShInvsnPIThR9p', '홍길동', 'ROLE_USER', default);

SELECT *FROM member_security;