-- ch07

USE 세계무역;

SELECT *FROM 부서;

-- INSERT : 레코드 추가하기
INSERT INTO 부서 (부서번호, 부서명)
VALUES ('A5', '마케팅부');

SELECT * FROM 제품;

INSERT INTO 제품 VALUES ('91', '연어피클소스', NULL, 5000, 40);

INSERT INTO 사원 (사원번호, 이름, 직위, 성별, 입사일) VALUES
('E20', '김사과', '수습사원', '남', CURDATE())
,('E21', '박나나나', '수습사원', '여', CURDATE())
,('E22', '정오렌지', '수습사원', '남', CURDATE());

DESC 사원;
SELECT *FROM 사원;

-- UPDATE : 레코드 수정하기
UPDATE 사원 SET 이름 = "김레몬", 주소 = '금천구'
WHERE 사원번호 = "E20";

UPDATE 제품 SET 포장단위 = '200 ml bottles', 단가 =5500
WHERE 제품번호 = 91;

-- DELETE : 레코드 삭제하기

DELETE FROM 제품 
WHERE 제품번호 = 91;

DELETE FROM 사원
WHERE 사원번호 IN ("E20", "E21", "E22");

-- 가장 최근에 입사한 3명 지우기
DELETE FROM 사원
ORDER BY 입사일 DESC
LIMIT 3;


-- ON DUPLICATE KEY UPDATE
SELECT *FROM 제품;

INSERT INTO 제품(제품번호, 제품명, 단가, 재고) 
 VALUES (91, '연어피클핫소스', 6000, 50)
 ON DUPLICATE KEY UPDATE
  제품명 = '연어피클핫소스', 단가 = 7000, 재고 = 60;


-- 연습문제
-- 1. 제품 테이블에 레코드를 추가하시오.
-- 제품번호: 95, 제품명: 망고베리 아이스크림, 포장단위 : 400g, 단가: 800, 재고: 30
INSERT INTO 제품 VALUES (95, '망고베리 아이스크림', '400g', 800, 30);

-- 2. 제품 테이블에 레코드를 추가하시오.
-- 제품번호: 96, 제품명: 눈꽃빙수맛 아이스크림, 단가: 2000
INSERT INTO 제품 (제품번호, 제품명, 단가)
VALUES (96, '눈꽃빙수맛 아이스크림', 2000);

-- 3. 문제2에서 추가한 96번 제품의 재고를 30으로 변경하시오.
UPDATE 제품 SET 재고 = 30 
WHERE 제품번호 = 96;

-- 4. 사원이 한 명도 존재하지 않는 부서를 부서 테이블에서 삭제하시오.
DELETE FROM 부서
WHERE NOT EXISTS (
		  SELECT 부서번호 
		  FROM 사원
		  WHERE 부서번호 = 부서.부서번호
		);


SELECT *FROM 부서
WHERE EXISTS (SELECT 사원.사원번호 FROM 부서, 사원 
WHERE 부서.부서번호 = 사원.부서번호);

SELECT 부서.부서번호 FROM 부서, 사원 
WHERE 부서.부서번호 = 사원.부서번호;

















