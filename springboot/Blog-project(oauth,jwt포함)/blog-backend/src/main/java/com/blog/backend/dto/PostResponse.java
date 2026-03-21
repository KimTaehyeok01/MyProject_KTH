package com.blog.backend.dto;

import com.blog.backend.entity.Post;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class PostResponse {

    private Long id;
    private String title;
    private String content;
    private String authorName;
    private String authorPicture;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private List<CommentResponse> comments;

    public PostResponse(Post post) {
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
        this.authorName = post.getAuthor().getName();
        this.authorPicture = post.getAuthor().getPicture();
        this.createdAt = post.getCreatedAt();
        this.updatedAt = post.getUpdatedAt();
        this.comments = post.getComments().stream()
                .map(CommentResponse::new)
                .collect(Collectors.toList());
    }
}
