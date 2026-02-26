package com.study.Ex15Board.dto;

import com.study.Ex15Board.domain.reply.Reply;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class ReplySaveRequestDto {
    private String replyName;
    private String replyContent;
    private Long replyBoardIdx; // 게시글 인덱스

    @Builder
    public ReplySaveRequestDto(String replyName, String replyContent,Long replyBoardIdx){
        this.replyName = replyName;
        this.replyContent = replyContent;
        this.replyBoardIdx = replyBoardIdx;
    }

    // DTO -> Entity

    public Reply toEntity(){
        return Reply.builder()
                .replyName(replyName)
                .replyContent(replyContent)
                .replyBoardIdx(replyBoardIdx)
                .build();
    }
}
