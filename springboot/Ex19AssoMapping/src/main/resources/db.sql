-- ============================================
-- JPA 연관관계 매핑 예제 SQL
-- 게시글(Board) - 댓글(Comment) : OneToMany / ManyToOne
-- ============================================

-- ★ 테이블 삭제 (순서 주의: 자식 테이블 → 부모 테이블)
DROP TABLE IF EXISTS comment;
DROP TABLE IF EXISTS board;

-- ============================================
-- 1. 테이블 생성 (DDL)
-- ============================================

-- 게시글 테이블 (부모, One 쪽)
CREATE TABLE board (
    id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(200) NOT NULL,
    content VARCHAR(2000)
);

-- FOREIGN KEY : 다른 테이블의 키 값을 필드의 키값으로 가진 것.
--               테이블과 테이블과의 관계를 맺는 가장 중요한 요소
-- 데이터베이스에서 한 테이블의 필드(컬럼)가 다른 테이블의 기본 키를 참조하여 두 테이블을 논리적으로 연결하는 키

-- 댓글 테이블 (자식, Many 쪽) → FK로 board 참조
CREATE TABLE comment (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    content  VARCHAR(1000) NOT NULL,
    board_id BIGINT NOT NULL,
    FOREIGN KEY (board_id) REFERENCES board(id)
);

-- ============================================
-- 2. 데이터 삽입 (INSERT)
-- ============================================

-- 게시글 데이터
INSERT INTO board (id, title, content) VALUES (1, 'JPA 공부', 'JPA 연관관계 매핑을 배워봅시다.');
INSERT INTO board (id, title, content) VALUES (2, 'Spring Boot 시작', '스프링 부트 프로젝트를 생성해봅시다.');
INSERT INTO board (id, title, content) VALUES (3, 'H2 데이터베이스', 'H2 인메모리 DB 사용법을 알아봅시다.');

-- 댓글 데이터 (board_id로 게시글 참조)
-- 게시글 1의 댓글
INSERT INTO comment (id, content, board_id) VALUES (1, '좋은 글이네요!', 1);
INSERT INTO comment (id, content, board_id) VALUES (2, '잘 배워갑니다.', 1);
INSERT INTO comment (id, content, board_id) VALUES (3, '감사합니다!', 1);
-- 게시글 2의 댓글
INSERT INTO comment (id, content, board_id) VALUES (4, '따라해봤는데 잘 됩니다!', 2);
INSERT INTO comment (id, content, board_id) VALUES (5, '초보자에게 좋은 글이에요.', 2);
-- 게시글 3은 댓글 없음

-- ============================================
-- 3. 데이터 조회 (SELECT)
-- ============================================

-- 전체 게시글 조회
SELECT * FROM board;

-- 전체 댓글 조회
SELECT * FROM comment;

-- 게시글별 댓글 조회 (JOIN)
SELECT b.title AS 게시글, c.content AS 댓글
FROM board b
JOIN comment c ON b.id = c.board_id
ORDER BY b.id, c.id;

-- 특정 게시글의 댓글 조회 (게시글 1번)
SELECT b.title AS 게시글, c.content AS 댓글
FROM board b
JOIN comment c ON b.id = c.board_id
WHERE b.id = 1;

-- 게시글별 댓글 수 조회
SELECT b.title AS 게시글, COUNT(c.id) AS 댓글수
FROM board b
LEFT JOIN comment c ON b.id = c.board_id
GROUP BY b.id, b.title;

-- ============================================
-- 4. 데이터 수정 (UPDATE)
-- ============================================

-- 게시글 제목 변경
UPDATE board SET title = 'JPA 심화 학습' WHERE id = 1;

-- 댓글 내용 변경
UPDATE comment SET content = '정말 좋은 글이네요!' WHERE id = 1;

-- ============================================
-- 5. 데이터 삭제 (DELETE)
-- ============================================

-- 특정 댓글 삭제
DELETE FROM comment WHERE id = 5;

-- 게시글 삭제 시 → 먼저 댓글(자식) 삭제 후 게시글(부모) 삭제
DELETE FROM comment WHERE board_id = 2;
DELETE FROM board WHERE id = 2;