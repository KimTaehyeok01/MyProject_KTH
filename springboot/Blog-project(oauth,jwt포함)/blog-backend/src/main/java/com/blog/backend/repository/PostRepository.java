package com.blog.backend.repository;

import com.blog.backend.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    // 제목 또는 내용으로 검색
    Page<Post> findByTitleContainingOrContentContaining(String title, String content, Pageable pageable);
}
