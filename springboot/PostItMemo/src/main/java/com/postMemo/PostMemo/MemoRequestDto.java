package com.postMemo.PostMemo;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemoRequestDto {
    private String title;
    private String content;
    private MemoEntity.MemoColor color;
    private Integer posX;
    private Integer posY;
    private Integer zIndex;

    public MemoEntity toEntity() {
        return MemoEntity.builder()
                .title(this.title)
                .content(this.content)
                .color(this.color)
                .posX(this.posX)
                .posY(this.posY)
                .zIndex(this.zIndex)
                .build();
    }

    public void applyTo(MemoEntity memo) {
        memo.update(this.title, this.content, this.posX, this.posY, this.zIndex);
    }
}