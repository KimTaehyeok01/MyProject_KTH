USE 세계무역;
-- 1. 제품 테이블의 재고 컬럼에 CHECK 제약조건을 추가하시오. ALTER TABLE 명령을 사용합니다.
-- 제약조건: 재고는 0보다 크거나 같아야 합니다.
ALTER TABLE 제품 ADD CONSTRAINT chk_제품_재고 CHECK(재고 >= 0);
-- ALTER TABLE 테이블명 ADD CONSTRAINT 제약조건명 제약조건(컬럼명);

-- 2. 제품 테이블에 재고금액 컬럼을 추가하시오. 이때 재고금액은 ‘단가 * 재고’가 자동 계산되어 
--    저장되도록 합니다.
-- ALTER TABLE 명령을 사용합니다.
-- STORED 옵션을 사용하면 됩니다.
ALTER TABLE 제품 ADD COLUMN 재고금액 INT GENERATED ALWAYS AS (단가 * 재고) STORED;

-- 3.제품 테이블에서 제품 레코드를 삭제하면 주문세부 테이블에 있는 관련 레코드도 
-- 함께 삭제되도록 주문세부 테이블의 제품번호 컬럼에 외래키 제약조건과 옵션을 설정하시오.

ALTER TABLE 주문세부 ADD CONSTRAINT fk_주문세부_제품 
FOREIGN KEY (제품번호) REFERENCES 제품(제품번호)
ON DELETE CASCADE;


-- 실전문제

-- 영화테이블
DESC MOVIE;
DESC MOVIE_REVIEW;
SELECT *FROM MOVIE;
SELECT *FROM MOVIE_REVIEW;
DROP TABLE MOVIE_REVIEW;
DROP TABLE MOVIE;


CREATE DATABASE MOVIE_DB;
USE MOVIE_DB;
CREATE TABLE MOVIE (
	MOVIE_NUMBER CHAR(5) PRIMARY KEY,
	TITLE VARCHAR(100) NOT NULL,
    GENRE VARCHAR(20) CHECK(GENRE IN ('코미디','드라마','다큐','SF','액션','역사','기타')),
    ACTOR VARCHAR(100) NOT NULL,
    direction VARCHAR(50) NOT NULL,
    Production VARCHAR(150) NOT NULL,
    Release_date DATE,
    Registration_date DATE DEFAULT(CURDATE())
);


CREATE TABLE MOVIE_REVIEW(
	REVIEW_NUMBER INT AUTO_INCREMENT PRIMARY KEY,
	REVIEWER VARCHAR(50) NOT NULL,
	MOVIE_NUMBER CHAR(5) NOT NULL,
	REVIEW_SCORE INT NOT NULL CHECK(REVIEW_SCORE BETWEEN 1 AND 5),
	REVIEW_TEXT VARCHAR(2000) NOT NULL,
	Registration_date DATE DEFAULT(NOW()),
	FOREIGN KEY(MOVIE_NUMBER) REFERENCES MOVIE(MOVIE_NUMBER)
);

-- 3.
INSERT INTO MOVIE 
VALUES ('00001','파묘','드라마','최민식, 김고은','장재현','쇼박스','2024-02-22', DEFAULT), 
	   ('00002','듄:파트2','액션','티미시 샬라메, 젠데이아','드니뵐뇌브','레전더리 픽쳐스','2024-02-28', DEFAULT);

-- 4.
INSERT INTO MOVIE_REVIEW (REVIEWER, MOVIE_NUMBER, REVIEW_SCORE, REVIEW_TEXT, Registration_date)
VALUES ('영화광','00001',5,'미치도록 스릴이 넘쳐요',DEFAULT);

INSERT INTO MOVIE_REVIEW (REVIEWER, MOVIE_NUMBER, REVIEW_SCORE, REVIEW_TEXT, Registration_date)
VALUES ('무비러브','00002',4,'장엄한 스케일이 좋다',DEFAULT);

-- 5. 영화번호를 00003으로도 새로운 레코드를 넣어서 오류 발생 여부를 확인하시오.
INSERT INTO MOVIE 
VALUES ('00003','파묘','드라마','최민식, 김고은','장재현','쇼박스','2024-02-22', DEFAULT), 

-- 6. 영화테이블에서 레코드를 지우면 외래키 제약조건에 의해 오류가 발생하는지 확인하시오.
DELETE FROM MOVIE WHERE MOVIE_NUMBER = '00001';


ALTER TABLE movie DROP FOREIGN KEY fk_MOVIE_NUMBER;

-- 7. ON CASCADE 옵션을 통해 6번 문제를 해결하시오.
ALTER TABLE MOVIE ADD CONSTRAINT fk_MOVIE_NUMBER 
FOREIGN KEY(MOVIE_NUMBER) REFERENCES MOVIE_REVIEW(MOVIE_NUMBER)
ON UPDATE CASCADE ON DELETE CASCADE;

SELECT *
FROM MOVIE A, MOVIE_REVIEW B
WHERE A.MOVIE_NUMBER = B.MOVIE_NUMBER;


















