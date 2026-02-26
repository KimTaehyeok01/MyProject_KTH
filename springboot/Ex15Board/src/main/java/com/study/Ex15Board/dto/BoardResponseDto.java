package com.study.Ex15Board.dto;

// ResponseDto -> 폼(Json) 데이터 요청 데이터 매핑
// ResponseDto -> Json 데이터 응답

// HTTP Request <-> DTO <-> Entity <-> Repository <-> Controller/Service <-> View(Response)

import com.study.Ex15Board.domain.board.Board;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BoardResponseDto {
    private Long boardIdx;
    private String boardName;
    private String boardTitle;
    private String boardContent;
    private Integer boardHit;
    private LocalDateTime boardDate;

    // Entity -> DTO 생성자함수
    public BoardResponseDto(Board entity){
        this.boardIdx = entity.getBoardIdx();
        this.boardName = entity.getBoardName();
        this.boardTitle = entity.getBoardTitle();
        this.boardContent = entity.getBoardContent();
        this.boardHit = entity.getBoardHit();
        this.boardDate = entity.getBoardDate();
    }
}
