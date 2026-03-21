package com.blog.backend.dto;

import com.blog.backend.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CommentResponse {

    private Long id;
    private String content;
    private String authorName;
    private String authorPicture;
    private LocalDateTime createdAt;

    public CommentResponse(Comment comment) {
        this.id = comment.getId();
        this.content = comment.getContent();
        this.authorName = comment.getAuthor().getName();
        this.authorPicture = comment.getAuthor().getPicture();
        this.createdAt = comment.getCreatedAt();
    }
}
