package com.Blog.Blog.dto;

import com.Blog.Blog.entity.BlogUserInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class AddUserRequest {
    private String userId;
    private String userPassword;
    private String userEmail;
    private String userName;

    // DTO를 엔티티로 변환하는 메서드 (편리함!)
    @Builder
    public BlogUserInfo toEntity() {
        return BlogUserInfo.builder() // Entity에 @Builder가 있다면
                .userId(userId)
                .userPassword(userPassword)
                .userEmail(userEmail)
                .userName(userName)
                .build();
    }
}