package com.notice_board.notice_board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    // 기본적인 CRUD(저장, 조회, 삭제) 기능이 상속되어 있습니다.
}