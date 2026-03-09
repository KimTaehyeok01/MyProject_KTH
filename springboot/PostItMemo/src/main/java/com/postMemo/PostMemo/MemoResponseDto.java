package com.postMemo.PostMemo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class MemoResponseDto {
    private Long id;
    private String title;
    private String content;
    private String color;  
    private Integer posX;
    private Integer posY;
    private Integer zIndex;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public MemoResponseDto(Long id, String title, String content, String color,
                           Integer posX, Integer posY, Integer zIndex, LocalDateTime createdAt,
                           LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.zIndex = zIndex;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public static MemoResponseDto from(MemoEntity memo) {
        return new MemoResponseDto(
                memo.getId(),
                memo.getTitle(),
                memo.getContent(),
                memo.getColor() != null ? memo.getColor().name() : "yellow",
                memo.getPosX(),
                memo.getPosY(),
                memo.getZIndex(),
                memo.getCreatedAt(),
                memo.getUpdatedAt()
        );
    }
}