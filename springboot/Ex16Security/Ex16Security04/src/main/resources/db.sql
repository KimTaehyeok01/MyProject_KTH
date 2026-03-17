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


-- sns-db.sql

-- 1. DB 연결
USE mydb;

DROP TABLE sns_user;

CREATE TABLE sns_user(
    id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL, -- 닉네임(별명)
    email   VARCHAR(255) NOT NULL, -- 이메일(계정)
    provider    VARCHAR(50) NOT NULL, -- oauth provider (google/kakao/naver)
    provider_id VARCHAR(100) NOT NULL, -- provider user id
    picture VARCHAR(255) NOT NULL, -- 프로필이미지 경로
    user_role   VARCHAR(255) DEFAULT 'ROLE_USER',
    created_date  DATE DEFAULT (current_date),
    UNIQUE KEY uq_sns_user_provider (provider, provider_id)
);

INSERT INTO sns_user(name, email, provider, provider_id, picture, user_role, created_date)
VALUES ('hong', 'hong@gmail.com', 'google', 'google_1001', '', 'USER', default );
INSERT INTO sns_user(name, email, provider, provider_id, picture, user_role, created_date)
VALUES ('tom', 'tom@gmail.com', 'kakao', 'kakao_1001', '', 'USER', default );

SELECT * FROM sns_user;

-- commit;

-- sns 회원가입은 간편하게 빨리 회원가입 하려는 목적.
-- sns는 필요한 회원정보에 제한이 있다. 몇개 안준다.
-- 예) 내 사이트는 sns 로그인만 지원하겠다? 가능하다.

-- 정식 회원가입은 절차가 까다롭고, 입력할 내용이 많음.
-- 추가적인 정보가 핗요하지 않은 장점이 있다.
-- 개인정보 보호 관리자가 있어야 하고, 유출에 대한 책임도 져야 한다.

-- 대안 : 제 3의 인증 사이트를 이용한다.
-- 예) Supabase, Firebase, cloudFlare

-- 서비스 구축시 : Google / 카카오 회원가입/인증만 지원한다.

-- sns-db.sql

-- 1. DB 연결
USE mydb;

DROP TABLE sns_user;

CREATE TABLE sns_user(
    id      BIGINT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name    VARCHAR(255) NOT NULL, -- 닉네임(별명)
    email   VARCHAR(255) NOT NULL, -- 이메일(계정)
    provider    VARCHAR(50) NOT NULL, -- oauth provider (google/kakao/naver)
    provider_id VARCHAR(100) NOT NULL, -- provider user id
    picture VARCHAR(255) NOT NULL, -- 프로필이미지 경로
    user_role   VARCHAR(255) DEFAULT 'ROLE_USER',
    created_date  DATE DEFAULT (current_date),
    UNIQUE KEY uq_sns_user_provider (provider, provider_id)
);

INSERT INTO sns_user(name, email, provider, provider_id, picture, user_role, created_date)
VALUES ('hong', 'hong@gmail.com', 'google', 'google_1001', '', 'ROLE_USER', default );
INSERT INTO sns_user(name, email, provider, provider_id, picture, user_role, created_date)
VALUES ('tom', 'tom@gmail.com', 'kakao', 'kakao_1001', '', 'ROLE_USER', default );

SELECT * FROM sns_user;

-- commit;