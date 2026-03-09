package com.postMemo.PostMemo;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "memo")
@Getter
@NoArgsConstructor
public class MemoEntity {

    public enum MemoColor {
        yellow, pink, blue, green, purple, orange
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Enumerated(EnumType.STRING)
    @Column(name = "color", nullable = false)
    private MemoColor color;

    @Column(name = "pos_x", nullable = false)
    private Integer posX;

    @Column(name = "pos_y", nullable = false)
    private Integer posY;

    @Column(name = "z_index", nullable = false)
    private Integer zIndex;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Builder
    public MemoEntity(String title, String content, MemoColor color, Integer posX, Integer posY, Integer zIndex) {
        this.title = title;
        this.content = content;
        this.color = color;
        this.posX = posX;
        this.posY = posY;
        this.zIndex = zIndex;
    }

    public void update(String title, String content, Integer posX, Integer posY, Integer zIndex) {
        this.title = title;
        this.content = content;
        this.posX = posX;
        this.posY = posY;
        this.zIndex = zIndex;
    }

    public void updateColor(MemoColor color) {
        this.color = color;
    }
}
