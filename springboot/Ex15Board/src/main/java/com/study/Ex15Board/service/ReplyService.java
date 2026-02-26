package com.study.Ex15Board.service;

import com.study.Ex15Board.domain.reply.Reply;
import com.study.Ex15Board.domain.reply.ReplyRepository;
import com.study.Ex15Board.dto.ReplyResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    // 댓글 목록 조회
    @Transactional(readOnly = true)
    // 댓글 목록 조회(게시글 DB인덱스가 같은 댓글 레코드를 조회한다.)
    public List<ReplyResponseDto> findAllByReplyBoardIdx(Long boardIdx){
        List<Reply> list = replyRepository.findAllByReplyBoardIdxOrderByReplyDateDesc(boardIdx);

        return list.stream().map(ReplyResponseDto::new).collect(Collectors.toList());
    }

    // 댓글 삭제
    // 댓글 저장
    // 댓글 있는지 조회

}
