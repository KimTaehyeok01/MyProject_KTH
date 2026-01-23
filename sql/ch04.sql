-- ch04
-- 집계함수(여러행 함수) : count, sum, avg, max, min, stddev(표준편차)

SELECT count(*) FROM 고객;

-- 집계함수는 null값을 제외한다.
SELECT count(고객번호), count(도시), count(지역) FROM 고객;

SELECT sum(마일리지) AS 평균
	,floor(avg(마일리지)) AS 평균
	,max(마일리지) AS 최대값
	,min(마일리지) AS 최소값
	,truncate(stddev(마일리지), -1) AS 표준편차
FROM 고객 WHERE 도시 = '서울특별시';

-- group by절 : 특정 컬럼에 대한 그룹으로 집계할 때
-- select 옆에 컬럼 이름은 (집계함수 제외) 반드시 group by절에도 써야함
SELECT 도시 
		,count(*) AS '도시별 고객수'
		,floor(avg(마일리지)) AS '도시별 평균 마일리지'
FROM 고객 
GROUP BY 도시;

SELECT 도시, 마일리지 
		,count(*) AS '도시별 고객수'
		,floor(avg(마일리지)) AS '도시별 평균 마일리지'
FROM 고객 
GROUP BY 1,2
ORDER BY 도시, 마일리지;


SELECT 담당자직위, 도시
		,count(*) AS 고객수
		,avg(마일리지) AS 평균
FROM 고객
GROUP BY 1,2
ORDER BY 1,2;

-- HAVING절 : SELECT문에 들어가는 컬럼과 집계함수에만 적용 가능
--          : GROUP BY절과 함께 사용 (GROUP BY 뒤에)
SELECT 도시
	,count(*) AS '도시별 고객수'	
FROM 고객
GROUP BY 도시
HAVING count(*) >= 10
ORDER BY 2 desc;

SELECT 도시	
	,count(*) AS '도시별 고객수'
	,avg(마일리지) '평균 마일리지'
FROM 고객
WHERE 도시 LIKE '%광역시' -- 잡계에 참여할 행을 미리 선별한다.
GROUP BY 도시
HAVING count(*) >= 5; -- 집계 후의 결과물에서 선별한다.

SELECT 도시, 담당자직위 ,sum(마일리지)
FROM 고객
WHERE 고객번호 LIKE 'T%'
GROUP BY 1,2
HAVING sum(마일리지) > 1000;


-- 연습문제
-- 1. 고객 테이블에서 담당자직위 별로 집계를 하되,
--   담당자직위와 최대 마일리지(Max() 함수)를 출력하시오.
-- 다만, 집계에 참여하는 고객은 '광역시'에 거주해야 함.
-- 최대 마일리지는 10000이상인 행만 출력하시오.
SELECT 담당자직위, max(마일리지) AS '최대 마일리지'
FROM 고객
WHERE 도시 LIKE '%광역시'
GROUP BY 1
HAVING max(마일리지) >= 10000;

-- count()함수에 distinct 예약어 추가
-- count(distinct 도시) : 중복값을 한번씩만 센다.
SELECT 도시, count(도시), count(DISTINCT 도시), sum(DISTINCT 도시)
FROM 고객
GROUP BY 도시;

SELECT count(도시) AS 전체데이터수 -- 93(전체 고객 수)
		,count(DISTINCT 도시) AS 거래도시수 -- 거래 도시 수는 27개
FROM 고객;

-- 주문년도별로 집계를 해보자
SELECT year(주문일) AS 주문년도
	  ,count(*) AS 주문건수
FROM 주문
GROUP BY year(주문일);

-- 분기별, 소계(ROLLUP)를 집계해보자
SELECT YEAR(주문일) AS 주문연도
		,quarter(주문일) AS 분기
		,count(*) AS 주문건수
FROM 주문
GROUP BY YEAR(주문일), quarter(주문일)
WITH rollup; -- 분류별 소계, 총계를 내주는 구문

-- 주문 테이블에서 요청일보다 발송이 늦어진 주문을
-- 월별로 집계(요약)해보자
SELECT month(주문일) AS 주문월
		,count(*) AS 주문건수
FROM 주문
WHERE 요청일 < 발송일
GROUP BY MONTH(주문일)
ORDER BY MONTH(주문일);

-- 제품 테이블에서 '아이스크림'이 들어간 제품들의 재고합을 
-- 집계하여 출력하시오.

SELECT 제품명, sum(재고) AS 재고합
FROM 제품
WHERE 제품명 LIKE '%아이스크림'
GROUP BY 1
WITH rollup;

-- 실전문제
-- 1. 주문세부 테이블에서 주문수량합과 주문금액합을 보이시오.
SELect sum(주문수량), sum(단가*주문수량) FROM 주문세부;

-- 2. 주문세부 테이블에서 주문번호별로 주문된 제품번호의 목록과 
--    주문금액합을 보이시오.
--  주문번호는 주문 건당 하나씩 발급됨.
SELECT 주문번호, 제품번호, sum(단가*주문수량) AS 주문금액합
FROM 주문세부
GROUP BY 주문번호, 제품번호
WITH ROLLUP
ORDER BY 1 desc;

SELECT *FROM 주문세부;
-- 3. 주문 테이블에서 2021년 주문내역에 대해서 고객번호별로 
-- 주문건수를 보이되, 주문건수가 많은 상위 3건의 고객의 정보만 보이시오.
SELECT 고객번호, count(*)
FROM 주문
WHERE 주문일 LIKE '2021%'
GROUP BY 고객번호 
ORDER BY count(주문일) desc
LIMIT 3;


SELECT 고객번호, count(*)
FROM 주문
WHERE year(주문일) = 2021
GROUP BY 고객번호 
ORDER BY count(주문일) desc
LIMIT 3;


-- GROUP_CONCAT() 함수 : 여러 행의 문자열을 결행해 줌.
-- GROUP_CONCAT(컬럼 SEPARATOR '구분자' )
SELECT 직위, GROUP_CONCAT(이름 SEPARATOR ', ')
FROM 사원
GROUP BY 직위
order BY 직위;

SELECT 직위, COUNT(직위), 이름
FROM 사원
GROUP BY 직위, 이름
order BY 직위;

SELECT * FROM 사원;


-- 복습 GROUP_CONCAT, WITH 



SELECT *FROM 주문;

SELECT *FROM  주문세부;

SELECT *FROM 주문 a INNER join 주문세부 b
ON a.주문번호 = b.주문번호;






