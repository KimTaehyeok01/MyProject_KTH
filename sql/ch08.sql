-- ch08
USE 세계학사;
-- DDL(Date Definition Language): 데이터 정의어

CREATE DATABASE IF NOT EXISTS 세계학사;

DROP DATABASE 세계학사;
DROP TABLE 세계무역.학과;

-- 컬럼명 데이터타입 정리
-- char(길이) : 고정길이 문자열(0~255자)
-- 예) 이름 char(5); '홍길동' -> '홍길동  '
--					'홍길동입니다' -> 5자를 넘어서 에러

-- varchart(길이) : 가변길이 문자열(0~16383자)
-- 예) 이름 varchar(5); '홍길동' -> '홍길동' '홍' -> '홍'
-- 자주 사용하는 문자열에 적합(내부 메커니즘)
-- 예) 계시판app - 글제목, 사용자이름

-- text : 큰 길이의 문자열(0~16383자), 자주 사용하지 않는 문자열
-- 예) 계시판app - 본문내용, 상세설명

-- int(길이) : 정수(+-21억)
-- 길이는 표현의 범위이다. ZEROFILL 예약어와 함께 사용하지 않으면,
-- 의미가 없음. 추천하지 않음.
-- 예) age INT(10) ZEROFILL; 30 -> 000000030

-- float : 실수(소수점 7자리)
-- date/time : 날짜/시간 따로
-- datetime : 날짜,시간 통합
-- timestamp : 날짜,시간 통합 -> UTC기준 

CREATE TABLE 학과 (
		학과번호 CHAR(2), -- 고정길이 99학과 이하, 2자리 -- 기본키(PK)
		학과명 varchar(20), -- 가변길이, 길이예측 어려움, 20자리 이하
		학과장명 varchar(20) -- 가변길이, 길이예측 어려움, 20자리 이하
);

DESC 학과;


INSERT INTO 학과 
VALUES ('AA', '컴퓨터공학과', '배경민')
	   ('BB', '소프트웨어학과', '김남준'),
	   ('CC', '디자인융합학과', '박선영');

SELECT *FROM 학과;

DROP TABLE 세계학사.학생;

CREATE TABLE 학생 (
		학번 CHAR(5), -- 기본키(PK)
		이름 VARCHAR(20),
		생일 DATE, -- 2000-03-10   다른 예) 2000/03/10 2000.03.10
		연락처 VARCHAR(20),
		학과번호 CHAR(2) -- 외래키(FK)
);
DESC 학생;
SELECT *FROM 학생;

INSERT INTO 학생
VALUES ('S0001', '이윤주', '2020/01/30', '01033334444', 'AA'),
	   ('S0002', '이승은', '2021-02-23', NULL, 'AA'),
	   ('S0003', '백재용', '2020-01-30', '01012345678', 'CC');

SELECT *FROM 학생;
DROP TABLE 학생;

ALTER TABLE 학생 ADD COLUMN 성별 CHAR(2);
ALTER TABLE 학생 MODIFY COLUMN 성별 CHAR(10);

-- 다른 테이블을 복사해서 테이블 생성하기
CREATE TABLE 휴학생 AS  -- AS는 별칭이 아니고 SELECT *FROM 학생인 학생 테이블을
--                        휴학생 테이블로 복사하여 테이블을 CREATE한다.
SELECT *FROM 학생;

-- 데이터만 지우기
TRUNCATE TABLE 휴학생;

SELECT *FROM 휴학생;
DESC 휴학생;
DROP TABLE 휴학생;

-- 구조만 복사하기
CREATE TABLE 휴학생 AS 
SELECT *FROM 학생 WHERE 1=2;  -- 항상 FALSE인 조건(데이터는 복사 못하고 구조만 복사)

-- 가상컬럼(Generated Column) : 계산된 결과를 저장
CREATE TABLE 회원 (
-- 기본키 설정 : 중복된 데이터 허용 풀가(UNIQUE), not null속성.
		아이디 varchar(20) PRIMARY KEY,
		회원명 varchar(20),
		키 INT,
		몸무게 INT,
		-- INSERT 시에 자동계산되어 들어간다.
		체질량지수 DECIMAL(4,1) AS (몸무게 / POWER(키/100,2)) STORED
);
DESC 회원;

INSERT INTO 회원 (아이디, 회원명, 키, 몸무게)
VALUES ('ARANG', '김아랑', 170, 55);
SELECT *FROM 회원;

-- ALTER : 테이블(객체) 속성 변경
DESC 학생;
-- 컬럼 추가 
ALTER TABLE 학생 ADD COLUMN 성별 CHAR(1);

-- 컬럼 변경
ALTER TABLE 학생 CHANGE COLUMN 연락처 핸드폰번호 VARCHAR(20);

-- 컬럼 삭제 
ALTER TABLE 학생 DROP COLUMN 성별;

-- 테이블 이름 변경
ALTER TABLE 학생 RENAME 졸업생;
DESC 졸업생;

-- 테이블 삭제
DROP TABLE 졸업생;
DROP TABLE 학과;

-- 제약조건
CREATE TABLE 학과 (
	학과번호 CHAR(2) PRIMARY KEY, -- UNIQUE, NOT NULL 
	학과명 VARCHAR(20) NOT NULL,
	학과장명 VARCHAR(20) UNIQUE
);

DROP TABLE 학과;
DESC 학과;
SELECT *FROM 학과;

INSERT INTO 학과 
VALUES ('01', '국어국문학과', '홍교수');

INSERT INTO 학과 
VALUES ('01', '영문과', '데이비교수'); -- UNIQUE 제약조건 위배

INSERT INTO 학과 
VALUES (NULL, '영문과', '데이비교수'); -- NOT NULL 제약조건 위배

INSERT INTO 학과 
VALUES ('02', '영문과', '데이비교수');

CREATE TABLE 학과 (
	학과번호 CHAR(2),
	학과명 VARCHAR(20) NOT NULL,
	학과장명 VARCHAR(20) UNIQUE
	PRIMARY KEY(학과번호)
);

CREATE TABLE 학과 (
	학과번호 CHAR(2),
	학과명 VARCHAR(20) NOT NULL,
	학과장명 VARCHAR(20) UNIQUE
);

ALTER TABLE 학과 ADD CONSTRAINT PK_학과 PRIMARY KEY(학과번호);

DROP TABLE 학생;
-- 외래키(FOREIGN KEY) 제약조건 추가
CREATE TABLE 학생 (
	학번 CHAR(5) PRIMARY KEY,
	이름 VARCHAR(20) NOT NULL,
	생일 DATE NOT NULL,
	연락처 VARCHAR(20) UNIQUE,
	학과번호 CHAR(2), 
	성별 CHAR(1) CHECK (성별 IN('남', '여')) NOT NULL,
	-- 등록일이 입력 안되면(NULL을 넣으면 NULL이 들어감. NULL도 하나의 값)
	등록일 DATE DEFAULT(NOW()),
	-- 학과번호 INSERT시에, 학과 테이블의 학과번호에 있는 것이어야 됨.
	FOREIGN KEY (학과번호) REFERENCES 학과(학과번호) -- 외래키 졔약조건
);
DESC 학생;

SELECT *FROM 학과;
SELECT *FROM 학생;

INSERT INTO 학과
VALUES ('01', '국어국문과', '홍교수');

INSERT INTO 학생
VALUES ('S0001', '강감찬', '2000-02-03', '01022223333', '01', '남',NULL);

-- CHECK 제약조건 테스트
INSERT INTO 학생 (학번,이름, 생일 ,연락처, 학과번호, 성별)
VALUES ('S0002', '이순신', '2000-02-03', '01021223333', '01', '남');

-- 외래키 제약조건 
INSERT INTO 학생 (학번,이름, 생일 ,연락처, 학과번호, 성별)
VALUES ('S0004', '이순신', '2000-02-03', '01021223321', '01', '남');

-- ON DELETE/UPDATE CASCADE 
-- 참조하는 부모 테이블에서 삭제/수정이 일어날 때, 자식 테이블도  
-- 자동으로 변경/삭제하도록 한다.
CREATE TABLE 학과 (
	학과번호 CHAR(2) PRIMARY KEY,
	학과명 VARCHAR(20) NOT NULL UNIQUE
);

CREATE TABLE 학생 (
	학번 CHAR(5) PRIMARY KEY,
	이름 VARCHAR(20) NOT NULL,
	학과번호 CHAR(2),
	FOREIGN KEY (학과번호) REFERENCES 학과(학과번호)
	ON DELETE CASCADE ON UPDATE CASCADE
);
DROP TABLE 학생;
DROP TABLE 학과;
SELECT *FROM 학생;
SELECT *FROM 학과;

-- 학과 데이터
INSERT INTO 학과 VALUES ('01','국어국문과');
INSERT INTO 학과 VALUES ('02','컴퓨터공학과');

-- 학생 데이터 
INSERT INTO 학생 VALUES ('S0001', '홍길동', '01'), ('S0002', '이소룡', '01'), ('S0003', '이소룡', '03');

-- 학과번호 수정하면, 참조하던 학생 테이블의 학과번호도 함께 수정된다.
UPDATE 학과 SET 학과번호 = '03' WHERE 학과번호 = '02';ㅣ

-- 학과번호 삭제하면, 참조하던 학생 테이블의 레코드도 함께 삭제된다.
DELETE FROM 학과 WHERE 학과번호 = '01';

-- 연습문제 / 실전문제


















