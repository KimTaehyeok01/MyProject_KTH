--  데이터베이스 생성
CREATE DATABASE IF NOT EXISTS mydb;

-- 데이터베이스 전환
use mydb;

-- 회원 테이블 생성
-- INT(10) : 정수 10자리 할당(고정길이) -> 10자리 이상의 숫자를 넣으면 오류 (예: 0000012345)
-- VARCHAR(50) : 문자열 길이 50할당(가변길이) -> (50자리 미만의 문자를 넣으면, 그만큼만 메모리 확보), 50자리 이상의 문자를 넣으면 오류
-- PRIMARY KEY : 기본키, 유일성!!, 열과 열을 구분하는 식별자(예: 주민번호)
-- AUTO_INCREMENT : INSERT할 때 1씩 증가하는 속성을 추가
-- AUTO_INCREMENT를 지정하는 열에는 꼭 PRIMARY KEY (기본키)로 지정해줘야 합니다.
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
-- order, user, desc, group는 예약어라서 `order`, `user`같이 묶어주면 됨.
INSERT INTO MEMBER (member_no, member_id, MEMBER_password, member_nickname) VALUES
(1, 'hong', '1234', '홍길동');

-- mydb.MEMBER는 mydb안에 있는 테이블이 member이기에 저렇게 쓸 수도 있음.
INSERT INTO mydb.MEMBER (member_no, member_id, MEMBER_password, member_nickname) VALUES
(1, 'hong', '1234', '홍길동');

-- 모든 컬럼의 데이터를 value뒤에 기입하면 필드(컬럼) 생략가능
INSERT INTO `MEMBER` VALUES (2, 'lee', '1234', '이순신');
DELETE FROM MEMBER WHERE 컬럼명 = '삭제할_값';

SELECT *FROM MEMBER;
drop TABLE `MEMBER`;

-- auto_incremnet 속성은 0으로 추가하면 자동증가됨
INSERT INTO MEMBER VALUES (0, 'park', '1234', '박수다');

-- sql예약어(select, insert) : 대소문자를 구분하지 않는다.
-- 사용자 정의어(테이블명, 컬럼명) : 윈os - 구분하지 않는다. LinuxOS : 구분한다.
-- 데이터 값 : mySQL

-- 열(레코드) 삭제
DELETE FROM MEMBER WHERE member_no = 4;

-- 열(레코드) 수정하기
UPDATE MEMBER SET member_id = 'hong2', member_password='2222'
WHERE member_no =1;

-- 열의 개수 세기
select count(*) FROM MEMBER;

-- 커밋 : 실제 물리적 파일로 저장하는 명령어
-- mysql : auto commit - insert/update/delete 명령 후 자동 저장
-- oracle : menual commit - 직접 commit해야 저장됨.
COMMIT;


















