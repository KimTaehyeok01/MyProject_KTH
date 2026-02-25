package com.study.Ex15Board.domain.reply;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
    // 기본함수
    // 사용자 정의 함수
    // -게시글 인덱스로 댓글 목록 찾기
    List<Reply> findAllByReplyBoardIdxOrderByReplyDateDesc (Long replyBoardIdx);
}
