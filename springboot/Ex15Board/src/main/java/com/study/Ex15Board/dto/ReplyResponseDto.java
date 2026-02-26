package com.study.Ex15Board.dto;

import com.study.Ex15Board.domain.reply.Reply;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ReplyResponseDto {
    private Long replyIdx;
    private String replyName;
    private String replyContent;
    private LocalDateTime replyDate;
    private Long replyBoardIdx; // 게시글 인덱스

    // Entity -> Dto
    public ReplyResponseDto(Reply entity) {
        this.replyIdx = entity.getReplyIdx();
        this.replyContent = entity.getReplyContent();
        this.replyName = entity.getReplyName();
        this.replyDate = entity.getReplyDate();
        this.replyBoardIdx = entity.getReplyBoardIdx();
    }
}
