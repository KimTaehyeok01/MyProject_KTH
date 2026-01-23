-- ch05
USE 세계무역;
SELECT *FROM 고객;

-- JOIN : 2개 이상의 테이블을 조합하여 하나의 결과를 반환하는 문법
-- ANSI 조인 : 국제표준규격의 SQL문
-- Non ANSI 조인 : 각 브랜드마다 독자적 SQL문

-- 조인의 종류 : cross(on 필요x), inner, outer, self
-- 크로스 조인 : 테이블 A와 테이블 B에 모든 행의 조합, 카티션 곱(모든 경우의 수)

-- ANSI 조인 문법
SELECT count(*)
FROM 부서; -- 4개  -- 2컬럼(부서번호 부서명)

SELECT count(*)
FROM 사원; -- 10개 -- 1컬럼(사원번호 이름 영문이름..)

SELECT *FROM 사원; -- 10행 13열
SELECT *FROM 부서; -- 4행 2열

SELECT *
FROM 부서 
cross JOIN 사원; -- 40행

-- Non ANSI 조인 문법(생략어)
SELECT *
FROM 부서 
JOIN  사원;

-- 크로스 조인 활용 예
-- ansi
SELECT 부서.부서번호, 부서명, 이름, 사원.부서번호
FROM 부서  
CROSS JOIN 사원
WHERE 이름 ='배재용';

SELECT *FROM 사원; -- 10행 13열
SELECT *FROM 부서; -- 4행 2열

DESC 부서;
DESC 사원;

-- Non ansi
SELECT 부서.부서번호, 부서명, 이름, 사원.부서번호
FROM 부서 , 사원
WHERE 이름 ='배재용';

SELECT *FROM 부서, 사원;

SELECT 부서명, 이름, 성별 FROM 부서, 사원
WHERE 부서.부서번호 = 사원.부서번호;

-- INNER JOIN : 두 테이블 사이에 공통된 값을 기준으로 결과를 반환
-- 1. 등가조인(이퀴 조인) : =로 비교해서 조인한다.
-- 2. 비등가조인(논이퀴 조인) : 등호(=) 외에 다른 비교연산자로 비교하여 조인한다.

-- '이소미 사원의 사원번호, 직위, 부서번호, 부서명을 출력하시오.'
SELECT *FROM 사원; 
SELECT *FROM 부서; 

-- ansi
SELECT 사원.사원번호, 직위, 부서.부서번호, 부서명
FROM 사원 
inner JOIN 부서
ON 사원.부서번호 = 부서.부서번호
WHERE 이름 = '이소미';

-- Non ansi
SELECT 사원.사원번호, 직위, 부서.부서번호, 부서명
FROM 사원,부서
WHERE 사원.부서번호 = 부서.부서번호 AND 이름 = '이소미';

-- 조인 + group by절
-- 고객별 주문현황을 출력하는데, 고객번호, 담당자명, 고객회사명별 총 주문 건수를 구하고,
-- 주문 건수가 많은 순서대로 출력하시오.
SELECT *FROM 고객;
SELECT *FROM 주문;

SELECT 고객.고객번호, 담당자명, 고객회사명 , count(*) AS '총 주문 건수'
FROM 고객 
INNER JOIN 주문
ON 고객.고객번호 = 주문.고객번호
GROUP BY 1,2,3
ORDER BY '총 주문 건수' desc;

SELECT 고객.고객번호, 담당자명, 고객회사명 , count(*) AS '총 주문 건수'
FROM 고객, 주문
WHERE 고객.고객번호 = 주문.고객번호
GROUP BY 1,2,3
ORDER BY '총 주문 건수' desc;

-- 3개 테이블에서 inner 조인을 해보자.
-- 고객번호 별로 주문금액 합을 구하자.
SELECT 고객.고객번호, 담당자명, 고객회사명, sum(단가*주문수량) AS '주문금액 합'
FROM 고객
INNER JOIN 주문
ON 고객.고객번호 = 주문.고객번호
INNER JOIN 주문세부
ON 주문.주문번호 = 주문세부.주문번호
GROUP BY 1,2,3
ORDER BY 4 DESC;

SELECT 고객.고객번호, 담당자명, 고객회사명, sum(단가*주문수량) AS '주문금액 합'
FROM 고객, 주문,주문세부
where 고객.고객번호 = 주문.고객번호 AND 주문.주문번호 = 주문세부.주문번호
GROUP BY 1,2,3
ORDER BY 4 DESC;

-- inner join 비등가 조인(비교연산자 =제외, 동등한 값이 아닐 때)
DESC 고객; 
DESC 마일리지등급;

SELECT *FROM 고객;
SELECT *FROM 마일리지등급;

SELECT 고객번호, 고객회사명, 담당자명, 마일리지, 등급명
FROM 고객 
INNER JOIN 마일리지등급
ON 마일리지 BETWEEN 하한마일리지 AND 상한마일리지
WHERE 담당자명 = '이은광';

SELECT 고객번호, 고객회사명, 담당자명, 마일리지, 등급명
FROM 고객, 마일리지등급
WHERE (마일리지 BETWEEN 하한마일리지 AND 상한마일리지) AND 담당자명 = '이은광';

-- OUTER JOIN : 조건(등가,비등가)에 맞지 않는 행도 결과값으로 나옴
SELECT *FROM 부서;
SELECT *FROM 사원;

-- 총 사원 10명
SELECT *FROM 사원 WHERE 부서번호 = 'A4'; -- A4(홍보부)에 속한 사원이 없음

SELECT 부서명, 사원.*
FROM 사원
INNER JOIN 부서
ON 사원.부서번호 = 부서.부서번호; -- 정수진은 부서명이 없으므로 9명

-- 사원 테이블에서 부서번호가 null인 행도 출력하려고 한다.
SELECT 부서명, 이름
FROM 사원
LEFT OUTER JOIN 부서
ON 사원.부서번호 = 부서.부서번호; 

-- 부서번호가 NULL인 사원만 출력해보자.
SELECT 이름, 부서.*
FROM 사원
LEFT OUTER JOIN 부서
ON 사원.부서번호 = 부서.부서번호;
WHERE 부서.부서번호 IS NULL;


SELECT 부서.부서명, 사원.*
FROM 사원, 부서
WHERE 사원.부서번호(+)= 부서.부서번호; 

-- 셀프조인 : 한 개의 테이블을 대상으로 조인하는 것.
SELECT 상사번호, 이름, 직위
FROM 사원
WHERE 이름 = '이소미';

SELECT 상사번호, 이름
FROM 사원
WHERE 사원번호 = 'E06';

SELECT 사원.사원번호, 사원.이름,
		상사.사원번호 AS '상사의 사원번호',
		상사.이름 AS '상사의 이름'
FROM 사원
INNER JOIN 사원 AS 상사
ON 사원.상사번호 = 상사.상사번호;

SELECT *FROM 부서;
SELECT *FROM 사원;

-- 연습문제
-- 1. 세계무역 데이터베이스의 제품 테이블과 주문 세부 테이블을 조인하여 
--   제품명별로 주문수량합과 주문금액합을 보이시오.
SELECT 제품.제품명, sum(제품.재고) AS 주문수량
		,sum(제품.단가 * 제품.재고) AS 주문금액
FROM 제품, 주문세부
WHERE 제품.제품번호 = 주문세부.제품번호
GROUP BY 1;

-- 2. 주문, 주문세부, 제품 테이블을 활용하여 '아이스크림'제품에 대해서
-- (주문년도 제품명)별로 주문수량합을 보이시오.
SELECT *FROM 주문;
SELECT *FROM 주문세부;
SELECT *FROM 제품;

SELECT YEAR(주문.주문일) AS 주문년도, 제품.제품명, sum(제품.단가*주문세부.주문수량) AS 주문수량
FROM 제품 
INNER JOIN 주문세부
ON 제품.제품번호 = 주문세부.제품번호
inner JOIN 주문 
ON 주문세부.주문번호 = 주문.주문번호
WHERE 제품.제품명 LIKE '%아이스크림'
GROUP BY 1,2
ORDER BY 3 desc;

SELECT YEAR(주문.주문일) AS 주문년도, 제품.제품명, sum(제품.단가*주문세부.주문수량) AS 주문수량
FROM 제품, 주문세부, 주문
WHERE (제품.제품번호 = 주문세부.제품번호 AND 주문세부.주문번호 = 주문.주문번호)
AND 제품.제품명 LIKE '%아이스크림'
GROUP BY 1,2
ORDER BY 3 desc;

-- 3. 제품, 주문세부 테이블을 활용하여 제품명별로 주문수량합을 보이시오.
--   이때 주문이 한 번도 안 된 제품에 대한 정보도 함께 나타내시오.
SELECT *FROM 제품;
SELECT *FROM 주문세부;

SELECT 제품.제품명
	,sum(주문세부.주문수량) AS 주문수량합
FROM 제품, 주문세부
WHERE 제품.제품번호 = 주문세부.제품번호
GROUP BY 1;

-- 4. 고객 회사 중 마일리지 등급이 'A'인 고객의 정보를 조회하시오. 
--  조회할 컬럼은 고객번호, 담당자명, 고객회사명, 등급명, 마일리지입니다.
SELECT 고객번호, 담당자명, 고객회사명, 등급명, 마일리지
FROM 고객, 마일리지등급
WHERE (마일리지 BETWEEN 하한마일리지 AND 상한마일리지) AND 등급명 = 'A';




































