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