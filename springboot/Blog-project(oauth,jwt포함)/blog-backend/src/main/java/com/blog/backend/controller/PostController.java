package com.blog.backend.controller;

import com.blog.backend.dto.CommentRequest;
import com.blog.backend.dto.CommentResponse;
import com.blog.backend.dto.PostRequest;
import com.blog.backend.dto.PostResponse;
import com.blog.backend.entity.User;
import com.blog.backend.service.CommentService;
import com.blog.backend.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final CommentService commentService;

    // 전체 목록 조회
    @GetMapping
    public ResponseEntity<Page<PostResponse>> findAll(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.findAll(pageable));
    }

    // 검색
    @GetMapping("/search")
    public ResponseEntity<Page<PostResponse>> search(
            @RequestParam String keyword,
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(postService.search(keyword, pageable));
    }

    // 단건 조회
    @GetMapping("/{id}")
    public ResponseEntity<PostResponse> findById(@PathVariable Long id) {
        return ResponseEntity.ok(postService.findById(id));
    }

    // 글쓰기
    @PostMapping
    public ResponseEntity<PostResponse> save(
            @Valid @RequestBody PostRequest dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.save(dto, user));
    }

    // 수정
    @PutMapping("/{id}")
    public ResponseEntity<PostResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody PostRequest dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(postService.update(id, dto, user));
    }

    // 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable Long id,
            @AuthenticationPrincipal User user) {
        postService.delete(id, user);
        return ResponseEntity.ok().build();
    }

    // 댓글 작성
    @PostMapping("/{postId}/comments")
    public ResponseEntity<CommentResponse> saveComment(
            @PathVariable Long postId,
            @Valid @RequestBody CommentRequest dto,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(commentService.save(postId, dto, user));
    }

    // 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<Void> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal User user) {
        commentService.delete(commentId, user);
        return ResponseEntity.ok().build();
    }
}
