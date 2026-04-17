# SQLite3 실습 정리 02 - CRUD 함수형 예제

## 1. 실습 목표
- 학생 데이터에 대해 CRUD 기능 구현
- 함수 단위로 SQL 작업 분리
- 각 작업 결과를 콘솔로 확인

## 2. 파일 개요
- 파일명: `ex02.py`
- DB 파일: `student.db`
- 대상 테이블: `students`

## 3. 구현된 기능
- `create(name, age, grade)`
  - 학생 1건 추가
- `read(student_id)`
  - 특정 ID 학생 1건 조회
- `update(student_id, age, grade)`
  - 특정 ID 학생의 나이/학년 수정
- `delete(student_id)`
  - 특정 ID 학생 삭제
- `print_all()`
  - 전체 목록 출력

## 4. 실행 시나리오
1. 테이블 생성(없으면 생성)
2. 학생 3명 추가
3. 전체 조회
4. ID=1 상세 조회
5. ID=1 수정 후 전체 조회
6. ID=2 삭제 후 전체 조회
7. DB 연결 종료

## 5. 사용 SQL 요약
```sql
-- Create table
CREATE TABLE IF NOT EXISTS students (
    id    INTEGER PRIMARY KEY AUTOINCREMENT,
    name  TEXT    NOT NULL,
    age   INTEGER NOT NULL,
    grade TEXT
);

-- Create
INSERT INTO students (name, age, grade) VALUES (?, ?, ?);

-- Read one
SELECT * FROM students WHERE id = ?;

-- Read all
SELECT * FROM students;

-- Update
UPDATE students SET age = ?, grade = ? WHERE id = ?;

-- Delete
DELETE FROM students WHERE id = ?;
```

## 6. 학습 포인트
- 함수 분리로 코드 가독성과 유지보수성 향상
- `fetchone()`과 `fetchall()` 차이 이해
- `WHERE` 절 없이 `UPDATE`/`DELETE`를 실행하면 큰 사고로 이어질 수 있음

## 7. 리팩터링 제안
- 공통 예외 처리(`try/except/finally`) 추가
- 메뉴 기반 CLI(`input`)로 확장
- `update/delete` 실행 후 `rowcount` 확인으로 성공 여부 명확화

## 8. 노션 정리 팁
- 본 문서를 그대로 붙여넣으면 헤더/코드블록이 유지됨
- 실습 영상 또는 실행 캡처를 함께 첨부하면 보고서 완성도 상승
