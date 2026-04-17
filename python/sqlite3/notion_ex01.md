# SQLite3 실습 정리 01 - 테이블 생성 및 초기 데이터 조회

## 1. 실습 목표
- SQLite 데이터베이스 파일 연결
- `students` 테이블 생성
- 샘플 데이터 삽입
- 전체 데이터 조회 및 출력

## 2. 파일 개요
- 파일명: `ex01.py`
- DB 파일: `student.db`
- 테이블명: `students`

## 3. 핵심 코드 흐름
1. 경로 설정
- `BASE_DIR`로 현재 파일 위치를 구함
- `DB_PATH`를 `student.db`로 연결

2. DB 연결
- `sqlite3.connect(DB_PATH)`로 연결 객체 생성
- `cursor()`로 SQL 실행 커서 생성

3. 테이블 생성
- `CREATE TABLE IF NOT EXISTS students (...)`
- 주요 컬럼
  - `id`: 기본키, 자동 증가
  - `name`: 필수 문자열
  - `age`: 필수 정수
  - `grade`: 문자열

4. 데이터 삽입
- `executemany()`로 여러 학생 레코드 한 번에 삽입
- `INSERT OR IGNORE` 사용

5. 커밋 및 조회
- `conn.commit()`으로 반영
- `SELECT * FROM students` 후 `fetchall()`
- 표 형식으로 출력

## 4. 사용 SQL 요약
```sql
CREATE TABLE IF NOT EXISTS students (
    id      INTEGER PRIMARY KEY AUTOINCREMENT,
    name    TEXT    NOT NULL,
    age     INTEGER NOT NULL,
    grade   TEXT
);

INSERT OR IGNORE INTO students (name, age, grade)
VALUES (?, ?, ?);

SELECT * FROM students;
```

## 5. 학습 포인트
- SQLite는 파일 기반 DB라 초기 실습에 적합함
- 파라미터 바인딩(`?`)으로 안전하게 값 전달 가능
- SQLite는 수동 커밋이 기본이므로 `commit()` 호출이 중요함

## 6. 개선 아이디어
- `name` 컬럼에 `UNIQUE` 제약을 추가해 중복 이름 방지
- 데이터 삽입 전/후 행 개수 출력
- 조회 결과를 함수로 분리해 재사용성 향상

## 7. 실행 결과 예시
- ID, 이름, 나이, 학년이 정렬된 표 형태로 출력됨
- `student.db` 파일이 `sqlite3` 폴더에 생성됨
