package com.blog.backend.dto;

import com.blog.backend.entity.User;
import lombok.Getter;

@Getter
public class UserResponse {

    private Long id;
    private String name;
    private String email;
    private String picture;
    private String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.picture = user.getPicture();
        this.role = user.getRole().getValue();
    }
}
