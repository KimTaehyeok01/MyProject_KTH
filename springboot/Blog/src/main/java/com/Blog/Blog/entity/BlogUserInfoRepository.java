package com.Blog.Blog.entity; // 패키지 경로는 태혁님 프로젝트에 맞게 수정하세요

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlogUserInfoRepository extends JpaRepository<BlogUserInfo, Long> {

    Optional<BlogUserInfo> findByUserId(String userId);
}