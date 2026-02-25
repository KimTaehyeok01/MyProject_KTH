package com.study.Ex15Board.domain.reply;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Entity
@Table(name = "Reply")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Reply {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reply_idx", nullable = false)
    private Long replyIdx;
    @Column(name = "reply_name", nullable = false)
    private String replyName;
    @Column(name = "reply_content", nullable = false)
    private String replyContent;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "reply_date", nullable = false)
    private LocalDateTime replyDate = LocalDateTime.now();

    @Column(name = "reply_board_idx", nullable = false)
    private Long replyBoardIdx;

    @Builder
    public Reply(String replyName, String replyContent, Long replyBoardIdx) {
        this.replyName = replyName;
        this.replyContent = replyContent;
        this.replyBoardIdx = replyBoardIdx;
        this.replyDate = LocalDateTime.now();
    }

    public void update(String replyName, String replyContent){
        this.replyName = replyName;
        this.replyContent = replyContent;
        this.replyDate = LocalDateTime.now();
    }
}