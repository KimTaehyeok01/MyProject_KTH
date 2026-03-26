package com.study.LibraryStie.service;

import com.study.LibraryStie.entity.snsUser.SnsUser;
import lombok.Getter;

import java.io.Serializable;

// 소셜 로그인 후 세션에 저장하는 DTO
// SnsUser 엔티티를 그대로 세션에 넣지 않는 이유:
// 1. 직렬화 문제 방지
// 2. 불필요한 정보 제거
@Getter
public class SessionUser implements Serializable {

    private String name;    // 이름
    private String email;   // 이메일
    private String picture; // 프로필 이미지

    public SessionUser(SnsUser user) {
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
    }
}
