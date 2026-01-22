-- ch03.sql
-- 내장 함수 : MySQL에서 기본적으로 지원하는 함수
-- 1. 단일행 함수
-- 2. 여러행 함수(집계 함수)

-- CHAR_LENGTH, LENGTH 문자열의 길이
-- CHAR_LENGTH : 문자(열)길이
-- LENGTH : 바이트 수(영문자-ASCII, 한글,중,일-유니코드)
SELECT CHAR_LENGTH('hello')
		,LENGTH('hello');

SELECT CHAR_LENGTH('한글')
		,LENGTH('한글'); -- 6(한 글자에 3바이트)

-- 문자열 연결
SELECT concat('Dreams','Come', 'True');
-- WS : With Seperator의 약자
SELECT concat_ws('-', '2026', '01', '26') AS 날짜;

-- 문자열 일부 가져오기
SELECT left('SQL 완전정복', 3);
SELECT RIGHT('SQL 완전정복', 4);

SELECT Substr('SQL 완전정복', 3, 4); -- Substring, substr 아무거나 가능. 둘이 같음
SELECT Substr('SQL 완전정복', 2);

-- 문자열 일부 가져오기(구분자 이용)
-- 2번째 구분자 이후를 지우고 가져온다.
SELECT SUBSTRING_INDEX('서울시 동작구 흑석동', ' ', 2);
-- -2번째 구분자 이후를 지우고 가져온다.
SELECT SUBSTRING_INDEX('서울시 동작구 흑석동', ' ', -2);

-- 자릿수 채우기 함수
-- LPAD, RPAD : LEFT, RIGHT
SELECT LPAD('SQL', 10, "#");
SELECT RPAD('SQL', 10, "#");
SELECT RPAD(123, 10, "A");

-- 공백 제거
-- LPAD, RPAD : LEFT, RIGHT
SELECT LENGTH(LTRIM(' SQL '));
SELECT LENGTH(RTRIM(' SQL '));
SELECT LENGTH(TRIM(' SQL '));
--                             공백  공백X  : 문자열에 있는 공백을 전부 지워서 출력하라.
SELECT REPLACE(' I LIKE SQL ', ' ', '');

-- 특정 문자 제거(TRIM)
SELECT TRIM(BOTH '###' from '###SQL###'); -- SQL
SELECT TRIM(BOTH '#' from '###SQL###'); -- SQL

SELECT TRIM(BOTH '##' from '###SQL###'); -- ##SQL##

SELECT TRIM(BOTH 'abc' FROM 'abcSQLLababc');
SELECT TRIM(leading 'abc' FROM 'abcSQLLababc'); -- SQLLababc
SELECT TRIM(trailing 'abc' FROM 'abcSQLLababc'); -- abcSQLLab

-- 문자열 인덱스 찾기
SELECT field('JAVA', 'SQL', 'JAVA', 'C'); -- 2인덱스(두번째)
SELECT FIND_IN_SET('JAVA', 'SQL,JAVA,C'); -- 2인덱스
SELECT INSTR('네 인생을 살아라', '인생'); -- 3인덱스
SELECT ELT(2, 'SQL', 'JAVA', 'C'); -- 2인덱스인 자바 반환

-- 문자열 중복
SELECT repeat('*', 5);
SELECT concat( repeat('*', 5), 'star');
SELECT concat('hello', ' ', '김태혁');

-- 문자열 치환
SELECT REPLACE('010.123.4567', '.',  '-');

-- 문자열 거꾸로
SELECT reverse('olleh');

-- 소수점 관련 함수들 
-- 올림 : 0 이상이면 숫자를 올림(자릿수 증가)
SELECT ceiling(123.56); -- 소수점 첫재짜리에서 올림
SELECT floor(123.56); -- 소수점 첫재짜리에서 버림
SELECT round(123.56); -- 소수점 첫째짜리에서 반올림

SELECT round(123.56, 1); -- 소수점 둘째자리에서 반올림
SELECT round(123.567, 2); -- 소수점 셋째자리에서 반올림
SELECT round(3456.1234, -1); -- 일의 자리에서 반올림
SELECT round(3456.1234, -2); -- 십의 자리에서 반올림

--  floor()함수에서는 두번째 매개변수(인자)를 사용할 수 없음.
SELECT truncate(3456.1234, 1); -- 소수점 둘째자리에서 버림
SELECT truncate(3456.1234, 2); -- 소수점 둘째자리에서 버림
SELECT truncate(3456.1234, -1); -- 일의 자리에서 버림
SELECT truncate(3456.1234, -2); -- 십의 자리에서 버림


-- 절댓값
SELECT abs(-120);
SELECT abs(+120);

-- 부호 연산자
SELECT sign(-120); -- 음수이므로 0 리턴
SELECT sign(120); -- 양수이므로 1 리턴

-- 나누기 함수
SELECT 203 % 4;
SELECT 203 mod 4;
SELECT MOD(203, 4);

-- 제곱승
SELECT power(2,3);

-- 제곱근
SELECT sqrt(16);

-- 랜덤값
SELECT rand(); -- js rand()함수와 유사. 0.0 ~ 0.999999..
-- 1부터 100까지 랜덤 정수
SELECT floor(rand() * 100)+1;


-- 현재 날짜 시간 가져오기
SELECT now(), sysdate(); -- 둘이 같음
-- now() : 쿼리가 시작된 시각 (기본으로 사용하면 됨)
-- sysdate() : 함수가 호출된 실시간(찰나)
SELECT now(), sleep(2), sysdate();

-- 현재 날짜 가져오기
SELECT curdate();
-- 현재 시간 가져오기
SELECT curtime();

-- 날짜 간격 가져오기 (예-설날까지 남은 날짜 d-day) : DATEDIFF()
SELECT now()
		,DATEDIFF('2026-02-16', now())
		,DATEDIFF(now(), '2026-02-16');

-- 날짜 차이 계산
SELECT now()
		,TIMESTAMPDIFF(YEAR,now(), '2027-01-30')
		,TIMESTAMPDIFF(month,now(), '2027-01-30')
		,TIMESTAMPDIFF(day,now(), '2027-01-30');

SELECT now()
		,DATEDIFF('2026-01-22 10:00', now()) -- 자정 기준
		,TIMESTAMPDIFF(day,now(), '2026-01-22 10:00'); -- 만 24시 기준

-- 몇일 후 계산
SELECT now()
		,ADDDATE(now(), 5)
		,ADDDATE(now(), INTERVAL 50 day)
		,ADDDATE(now(), INTERVAL 50 month)
		,ADDDATE(now(), INTERVAL 50 year);

SELECT now()
		,last_day(now()) -- 이번달의 마지막 일(29,30,31)
		,dayofyear(now()) -- 올해 1월 1일에서 몇번째 날인가? 21번째 날
		,monthname(now()) -- 이번달 영어 이름
		,weekday(now()); -- 월(0)~일(6) 오늘 수요일(2)

		
-- 행 변환 함수
SELECT CAST('1' AS unsigned); -- 부호 없는 숫자로 변환
SELECT CAST('-1' AS unsigned); -- 오류(언더플로우)
SELECT CAST('-1' AS SIGNED); -- 부호(-)를 가진 숫자로 변환

SELECT CAST('1' AS unsigned); -- 둘다 같음
SELECT convert('1', unsigned); -- 둘다 같음

SELECT cast(2 as CHAR(1)); -- 문자를 한 자리로 변환 -> '2'
SELECT convert(2, char(1));

-- cast() : ANSI 표준 (추천)
-- convert() : MySQL 전용

-- 조건 함수(js 삼항연산자와 유사)
SELECT if(10 > 10, '10', '20');
SELECT if(12500 * 450 >  500000, '초과달성', '미달성') AS 달성여부;

-- null 체크 함수
SELECT IFNULL('123', 0); -- 1항이 null이 아니면 1항을, 1항이 null이면, 2항을 반환한다.
SELECT IFNULL(null, 0); -- null이므로 0을 반환
SELECT IFNULL(NULL, '지역명 없음.');

SELECT nullif(12 * 10, 120); -- 1항과 2항이 같으면 null반환
SELECT nullif(12 / 0, 1200);
SELECT nullif(12 * 10, 1200); -- 1항과 2항이 같지않으면 1항 반환

-- case when구문(js if else구문 유사)
SELECT CASE 
		WHEN 20 < 30 THEN '20보다 작음'		
		WHEN 20 < 30 THEN '30보다 작음'	
		ELSE '그 외에 수'
END AS 결과;

-- 연습문제
-- 1. 다음 조건에 따라 고객 테이블에서 고객회사명과 전화번호를 
--    다른 형태로 보이도록 함수를 사용해봅시다. 
-- 고객회사명2와 전화번호2를 만드는 조건은 아래와 같습니다.
-- 조건
-- 1. 고객회사명2 : 기존 고객회사명 중 앞의 두 자리를 *로 변환한다.
-- 2. 전화번호2 : 기존 전화번호의 (xxx)xxx-xxxx 형식을 xxx-xxx-xxxx형식으로 변환한다.
SELECT * FROM 고객;

SELECT replace(고객회사명, LEFT(고객회사명, 2), '**') FROM 고객;
SELECT 전화번호 ,replace(replace(전화번호, '(', ''), ')', '-') AS 전화번호2 FROM 고객;

-- 2. 다음 조건에 따라 주문 세부 테이블의 모든 컬럼과 주문금액, 할인금액, 실제 주문금액을 보이시오. 
-- 이때 모든 금액은 1의 단위에서 버림을 하고 10원 단위까지 보이시오.
-- 조건
-- 1. 주문금액: 주문수량 * 단가
-- 2. 할인금액 : 주문수량 * 단가 * 할인율
-- 3. 실주문금액 : 주문금액 - 할인금액
SELECT *FROM 주문세부;

SELECT FLOOR(주문수량*단가) AS 주문금액 
			,FLOOR(주문수량*단가*할인율) AS 할인금액
			,(주문수량*단가)-floor(주문수량*단가*할인율) AS 실주문금액
			FROM 주문세부;
-- 3. 사원 테이블에서 전체 사원의 이름, 생일, 만나이, 입사일, 입사일수, 
-- 입사한 지 500일 후의 날짜를 보이시오.
SELECT *FROM 사원;

SELECT 이름, 생일, TIMESTAMPDIFF(YEAR, 생일, CURDATE()) AS 만나이, 입사일
		,datediff(curdate(), 입사일) AS '입사일 수'
		,ADDDATE(입사일, INTERVAL 500 day) AS '입사후 500일 후'
		FROM 사원;

-- 4. 고객 테이블에서 도시 컬럼의 데이터를 다음 조건에 따라 ‘대도시’와 ‘도시’로 구분하고, 
-- 마일리지 점수에 따라서 ‘VVIP’, ‘VIP’, ‘일반 고객’으로 구분하시오.
-- 조건
-- 1. 도시 구분: ‘특별시’나 ‘광역시’는 ‘대도시’로, 그 나머지 도시는 ‘도시’로 구분한다.
-- 2. 마일리지 구분 : 마일리지가 100,000점 이상이면 ‘VVIP고객’, 10,000점 이상이면 ‘VIP고객’, 그 나머지는 ‘일반고객’으로 구분한다.
SELECT 마일리지, CASE 
		WHEN 마일리지 >= 100000 THEN 'VVIP고객' 
		WHEN 마일리지 >= 10000 THEN 'VIP고객' 
		ELSE '일반고객'
END AS 고객등급,
      도시, CASE
		WHEN 도시 LIKE '%특별시' THEN '대도시'
		WHEN 도시 LIKE '%광역시' THEN '대도시'
		ELSE '도시'
END AS 도시등급 FROM 고객;

-- if문으로 사용가능
SELECT 마일리지, CASE 
		WHEN 마일리지 >= 100000 THEN 'VVIP고객' 
		WHEN 마일리지 >= 10000 THEN 'VIP고객' 
		ELSE '일반고객'
END AS 고객등급,도시
		,if(도시 LIKE '%특별시' OR 도시 LIKE '%광역시', '대도시', '도시') 
	AS 도시등급
FROM 고객;

-- 5. 주문 테이블에서 주문번호, 고객번호, 주문일 및 주문년도, 분기, 
--   월, 일, 요일, 한글요일을 보이시오.

SELECT *FROM 주문;
-- DAYOFMONTH(주문일) , DAY(주문일) 둘이 같은 것임
SELECT 주문번호, 고객번호, 주문일
		,date_format(주문일, '%Y년')AS 주문년도
		-- YEAR(주문일)
		,QUARTER(주문일)AS 주문분기 -- QUARTER 1~12월을 4로 나눔. 1,2,3/1 4,5,6/2, ...
		,date_format(주문일, '%m월')AS 월
		-- DAY(주문일)
		,date_format(주문일, '%d일')AS 일
		,CONCAT(SUBSTR('월화수목금토일', WEEKDAY(주문일) + 1, 1), '요일')AS 한글요일
		FROM 주문;


-- 6. 주문 테이블에서 요청일보다 발송일이 7일 이상 늦은 주문내역을 보이시오.
SELECT *, datediff(발송일, 요청일) AS 지연일수 FROM 주문
		WHERE datediff(발송일, 요청일) >= 7;
-- 실전문제
-- 1. 고객테이블에서 이름에 ‘정’이 들어가는 담당자명을 검색하시오.
SELECT *FROM 고객 WHERE 담당자명 LIKE '%정%';

-- 2. 제품테이블에서 제품번호, 제품명, 재고, 재고구분을 보이시오.
--  -재고구분 : 재고가 100개 이상이면 ‘과다재고’, 10개 이상이면 ‘적정’, 
--              나머지는 ‘재고부족’
SELECT 제품번호, 제품명, 재고, 
       CASE WHEN 재고 >= 100 THEN '과다재고'
            WHEN 재고 >= 10 THEN '적정'
            ELSE '재고부족'
END AS 재고구분
FROM 제품;

-- 3. 사원테이블에서 입사한 지 40개월이 지난 사원을 찾아,
--    이름, 부서번호, 직위, 입사일, 입사일수, 입사개월수를 찾으시오.

SELECT *FROM 사원;

SELECT 이름, 부서번호, 직위, 입사일 
		, datediff(now(), 입사일) AS 입사일수
		, timestampdiff(MONTH,입사일, now()) AS 입사개월수
FROM 사원 WHERE timestampdiff(MONTH,입사일, now()) >= 40;








