CREATE DATABASE CourseRegistration;

-- 강좌 테이블
CREATE TABLE course (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    course_id   VARCHAR(20)  NOT NULL UNIQUE,
    course_name VARCHAR(100) NOT NULL,
    instructor  VARCHAR(50)  NOT NULL,
    credits     INT          NOT NULL,
    course_time VARCHAR(50)  NOT NULL,
    room        VARCHAR(50)  NOT NULL,
    capacity    INT          NOT NULL,
    enrolled    INT          DEFAULT 0,
    created_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- 학생 테이블
CREATE TABLE student (
    id           BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id   VARCHAR(20) NOT NULL UNIQUE,
    student_name VARCHAR(50) NOT NULL,
    department   VARCHAR(50) NOT NULL,
    grade        INT         NOT NULL,
    created_at   TIMESTAMP   NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 수강신청 테이블
CREATE TABLE enrollment (
    enrollment_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    student_id    VARCHAR(20) NOT NULL,
    course_id     VARCHAR(20) NOT NULL,
    enrolled_at   TIMESTAMP   DEFAULT CURRENT_TIMESTAMP,
    status        VARCHAR(20) DEFAULT 'ENROLLED'
);

-- 샘플 데이터 삽입
INSERT INTO course (course_id, course_name, instructor, credits, course_time, room, capacity, enrolled) VALUES
('CS101',   '컴퓨터 프로그래밍 기초', '김교수', 3, '월/수 10:00-11:30', '공학관 301', 40, 35),
('CS201',   '자료구조',               '이교수', 3, '화/목 13:00-14:30', '공학관 401', 40, 30),
('CS301',   '웹 프로그래밍',          '박교수', 3, '월/수 14:00-15:30', '공학관 501', 35, 28),
('MATH201', '이산수학',               '최교수', 3, '화/목 10:00-11:30', '자연관 201', 45, 40),
('ENG301',  '소프트웨어 공학',        '정교수', 3, '수/금 15:00-16:30', '공학관 302', 30, 25),
('DB401',   '데이터베이스',           '한교수', 3, '화/목 15:00-16:30', '공학관 402', 35, 32);

-- 샘플 학생 데이터
INSERT INTO student (student_id, student_name, department, grade) VALUES
('STU001', '김태혁', '컴퓨터공학', 3);

SELECT *FROM course;
SELECT *FROM student;
SELECT *FROM enrollment;