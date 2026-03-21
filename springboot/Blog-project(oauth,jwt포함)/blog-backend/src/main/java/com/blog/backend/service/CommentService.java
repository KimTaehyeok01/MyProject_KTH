package com.blog.backend.service;

import com.blog.backend.dto.CommentRequest;
import com.blog.backend.dto.CommentResponse;
import com.blog.backend.entity.Comment;
import com.blog.backend.entity.Post;
import com.blog.backend.entity.User;
import com.blog.backend.repository.CommentRepository;
import com.blog.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

    // 댓글 작성
    @Transactional
    public CommentResponse save(Long postId, CommentRequest dto, User author) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        Comment comment = Comment.builder()
                .content(dto.getContent())
                .post(post)
                .author(author)
                .build();

        return new CommentResponse(commentRepository.save(comment));
    }

    // 댓글 삭제
    @Transactional
    public void delete(Long commentId, User user) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("댓글을 찾을 수 없습니다."));

        if (!comment.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        commentRepository.delete(comment);
    }
}
