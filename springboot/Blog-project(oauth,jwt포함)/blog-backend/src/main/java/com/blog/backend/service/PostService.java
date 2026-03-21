package com.blog.backend.service;

import com.blog.backend.dto.PostRequest;
import com.blog.backend.dto.PostResponse;
import com.blog.backend.entity.Post;
import com.blog.backend.entity.User;
import com.blog.backend.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    // 전체 목록 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<PostResponse> findAll(Pageable pageable) {
        return postRepository.findAll(pageable).map(PostResponse::new);
    }

    // 검색
    @Transactional(readOnly = true)
    public Page<PostResponse> search(String keyword, Pageable pageable) {
        return postRepository.findByTitleContainingOrContentContaining(keyword, keyword, pageable)
                .map(PostResponse::new);
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public PostResponse findById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));
        return new PostResponse(post);
    }

    // 글쓰기
    @Transactional
    public PostResponse save(PostRequest dto, User author) {
        Post post = Post.builder()
                .title(dto.getTitle())
                .content(dto.getContent())
                .author(author)
                .build();
        return new PostResponse(postRepository.save(post));
    }

    // 수정
    @Transactional
    public PostResponse update(Long id, PostRequest dto, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 본인 글인지 확인
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("수정 권한이 없습니다.");
        }

        post.update(dto.getTitle(), dto.getContent());
        return new PostResponse(post);
    }

    // 삭제
    @Transactional
    public void delete(Long id, User user) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("게시글을 찾을 수 없습니다."));

        // 본인 글인지 확인
        if (!post.getAuthor().getId().equals(user.getId())) {
            throw new IllegalArgumentException("삭제 권한이 없습니다.");
        }

        postRepository.delete(post);
    }
}
