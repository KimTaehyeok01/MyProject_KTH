package com.Blog.Blog.entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BlogRepository extends JpaRepository<BlogEntity, Long> {
    List<BlogEntity> findByUserTitleContaining(String userTitle);
}
