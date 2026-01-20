--  데이터베이스 생성
CREATE DATABASE IF NOT EXISTS mydb;

-- 데이터베이스 전환
use mydb;

-- 회원 테이블 생성
-- INT(10) : 정수 10자리 할당(고정길이) -> 10자리 이상의 숫자를 넣으면 오류 (예: 0000012345)
-- VARCHAR(50) : 문자열 길이 50할당(가변길이) -> (50자리 미만의 문자를 넣으면, 그만큼만 메모리 확보), 50자리 이상의 문자를 넣으면 오류
-- PRIMARY KEY : 기본키, 유일성!!, 열과 열을 구분하는 식별자(예: 주민번호)
-- AUTO_INCREMENT : INSERT할 때 1씩 증가하는 속성을 추가
CREATE TABLE member (
member_no int(10) PRIMARY KEY AUTO_INCREMENT,
member_id varchar(50), -- 로그인 아이디
member_password varchar(50), -- 로그인 비밀번호
member_nickname varchar(50) -- 별명
);

-- 테이블 구조 확인할때
DESC MEMBER;

-- row : 행/레코드/데이터추가
-- ""와 ''을 구분하지 않고 둘다 사용 가능(구분하지 않음)
-- 백틱(`) : 예약어를 사용자 정의어로 사용시 사용가능.
INSERT INTO MEMBER (member_no, member_id, MEMBER_password, member_nickname) VALUES
(1, 'hong', '1234', '홍길동');

SELECT *FROM MEMBER;