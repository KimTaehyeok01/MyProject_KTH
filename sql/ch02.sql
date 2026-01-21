USE 세계무역;

SELECT *FROM 고객;

-- 컬럼명에 별명을 넣어줌. as 일부러 생략함
SELECT count(*) '행의 개수' FROM 고객;

-- 고객 테이블에서 고객번호, 담당자명, 고객회사명, 마일리지, 10% 
-- 인상된 마일리지를 조회하시오. 이때 마일리지는 ‘포인트’로, 인상된 마일리
-- 지는 ‘10%인상된 마일리지’로 별명을 붙입니다.
SELECT 고객번호, 담당자명, 고객회사명, 마일리지 '포인트', 마일리지 * 1.1 '10% 인상된 마일리지' FROM 고객;

--  고객 테이블에서 마일리지가 100,000점 이상인 고객의 고객번호, 
-- 담당자명, 마일리지를 조회하시오.
SELECT 고객번호, 담당자명, 마일리지 FROM 고객 WHERE 마일리지 >= 100000;

-- ‘서울특별시’에 사는 고객에 대해 고객번호, 담당자명, 도시, 마일
-- 리지를 조회하시오. 이때 마일리지가 많은 고객부터 순서대로 보입니다.
SELECT 담당자명, 도시, 마일리지 '포인트' FROM 고객 WHERE 도시 = '서울특별시' ORDER BY 포인트 DESC;

SELECT 담당자명, 도시, 마일리지 '포인트' FROM 고객 WHERE 도시 = '서울특별시' AND 마일리지 >= 10000 ORDER BY 마일리지 DESC;

-- limit n : 갯수 제한 -> 전체 컬럼데이터 중에서 순서대로 상위 n개만 가져오는 것.
SELECT *FROM 고객 LIMIT 3;


-- 마일리지 상위 3명 / 하위 3명
SELECT *FROM 고객 ORDER BY 마일리지 DESC LIMIT 3;
SELECT *FROM 고객 ORDER BY 마일리지 asc LIMIT 3;

-- Distinct
SELECT DISTINCT 도시 FROM 고객;

-- 산술연산자
SELECT 23+5 더하기
	,23-5 AS 빼기
	,23*5 곱하기
	,23/5 '나누기 몫(실수)'
	,23 DIV 5 AS '나누기 몫(정수)'
	,23 % 5 AS '나머지1'
	,23 MOD 5 AS '나머지2' ;

-- 비교연산자
-- MySQL/MariaDB -- 불리언(boolean)을 정수형으로 처리한다.
--                  true는 1, false는 0(단 null과 비교는 NULL로 나옴)
-- PostgreSQL : true/false 텍스트형태의 불리언 값 반환
-- Oracle, MS, SQL Server - 불리언값을 직접 반환하지 않음
SELECT 23 > 23
	,23 < 23
	,23 = 23
	,23 = NULL
	,23 != 23
	,23 <> 23 -- <> : 같지 않은가?
	,23 >= 5
	,23 <= 5;
	
SELECT * FROM 고객 
WHERE 담당자직위 = '영업 과장';
	
SELECT * FROM 고객 
WHERE 도시 = '부산광역시' AND 마일리지 < 1000;

-- 연습문제 - 게시판에 스샷 제출
-- 1.‘서울’에 사는 고객 중에 마일리지가 15,000점 이상 20,000점 이하인 고객의 
--    모든 컬럼 정보를 보이시오.
-- 2. 세계무역의 고객들은 어느 지역, 어느 도시에 사는지 지역과 도시를 
-- 한 번씩만 보이시오.
--  이때 결과를 지역 순으로 나타내고, 동일 지역에 대해서는 도시 순으로 나타내시오.
-- DISTINCT는 두개의 컬럼에 적용
SELECT *FROM 고객
WHERE 도시 = '서울특별시' AND 마일리지 BETWEEN 15000 and 20000;

-- distinct 컬럼이 2개 이상이면, 쌍으로 구분된다.
-- (경기도, 광명시) <> (경기도, 구리시)
SELECT DISTINCT 지역, 도시 FROM 고객
 ORDER BY 지역, 도시;

SELECT 지역, 도시 FROM 고객;

-- 고객 테이블의 지역 필드가 ''(빈 문자열)로 추가되었다.
-- ''인지? ' '인지? null인지? 헷갈린다.
-- 지역필드의 값을 모두 '' -> null 바꿔준다.
-- 값이 없을 때는 명시적으로 null 값으로 채우는게 좋다.
UPDATE 고객 SET 지역 = NULL
WHERE 지역 ='';

SELECT 지역 FROM 고객;

SELECT *FROM 고객 WHERE 지역 ='';

-- union 연산자(2개 이상의 select 결과를 합친다.)
SELECT 고객번호, 도시, 마일리지 FROM 고객
WHERE 도시 = '부산광역시'
UNION 
SELECT 고객번호, 도시, 마일리지 FROM 고객
WHERE 마일리지 < 1000
ORDER BY 고객번호;

-- select 첫번재 필드 명으로 출력된다.
SELECT 고객번호 AS 고객넘버, 도시, 마일리지 FROM 고객
WHERE 도시 = '부산광역시'
UNION 
SELECT 고객번호, 도시, 마일리지 FROM 고객
WHERE 마일리지 < 1000
ORDER BY 고객넘버;

SELECT 고객번호, 도시, 마일리지 FROM 고객
WHERE 도시 = '부산광역시' 
OR 마일리지 < 1000 ORDER BY 고객번호;

-- union all과 union(union distinct)의 차이
-- union all은 중복된 행을 포함해서 모든 행을 출력(정렬 없이 그대로 합친다.)
-- union 중복된 행을 제거하고 출력(내부적으로 정렬 후 합친다.)

-- union 사용시 주의점
-- 1. 컬럼(필드)가 일치해야함
-- 2. 각 컬럼의 데이터 타입(숫자, 문자, 날짜)이 일치해야함


-- IS NULL연산자 : null값인지?
SELECT *FROM 고객
WHERE 지역 IS NOT NULL;

-- IN연산자 : ~중에 하나가 있으면 true. or연산자 대체
SELECT 고객번호, 담당자명, 담당자직위 FROM 고객
WHERE 담당자직위='영업 과장'
OR 담당자직위='마케팅 과장';

SELECT 고객번호, 담당자명, 담당자직위 FROM 고객
WHERE 담당자직위 in('영업 과장', '마케팅 과장');

-- BETWEEN AND : ~이상 ~이하 범위를 지정할 때
SELECT 담당자명, 마일리지
FROM 고객
WHERE 마일리지 >= 100000 AND 마일리지 <= 200000;

SELECT 담당자명, 마일리지
FROM 고객
WHERE 마일리지 BETWEEN 100000 AND 200000;


-- LIKE연산자 : 문자(열)의 일부를 검사할 때 사용
--           : % 여러 문자열을 대체
--           : _ 한 글자를 대체 

SELECT *FROM 고객
WHERE 도시 LIKE '%광역시'
AND 고객번호 LIKE '_C%';


-- 연습문제 - 스샷을 게시판에 제출
-- 1. 제품 테이블에서 제품명에 '주스'가 들어가는 모든 제품을 출력하시오.
-- 2. 제품 테이블에서 단가가 5,000원 이상 10,000원 이하인
--        '주스'가 제품명에 들어가는 제품들을 출력하시오.
-- 3. 제품 테이블에서 제품번호가 1,2,3,4,11,20인 모든 제품을 출력하시오.
-- 4. 제품 테이블에서 재고금액이 높은 상위 10개 제품에 대해 제품번호, 제품명, 
--   단가, 재고, 재고금액(단가 * 재고)을 보이시오.
SELECT *FROM 제품
WHERE 제품명 LIKE '%주스%';

SELECT *FROM 제품
WHERE 단가 BETWEEN 5000 and 10000 and 제품명 LIKE '%주스%';

SELECT *FROM 제품 WHERE 제품번호 IN(1,2,3,4,11,20);

SELECT *, (단가*재고) AS 재고금액 FROM 제품 
ORDER BY 재고금액 DESC LIMIT 10;






















	
	
	
	