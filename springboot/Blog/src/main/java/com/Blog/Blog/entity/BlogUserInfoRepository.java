package com.Blog.Blog.entity; // 패키지 경로는 태혁님 프로젝트에 맞게 수정하세요

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface BlogUserInfoRepository extends JpaRepository<BlogUserInfo, Long> {

    // 로그인할 때 아이디로 회원을 찾는 기능이 꼭 필요합니다!
    Optional<BlogUserInfo> findByUserId(String userId);
}