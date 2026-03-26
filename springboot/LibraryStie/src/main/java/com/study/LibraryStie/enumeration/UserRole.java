package com.study.LibraryStie.enumeration;

import lombok.Getter;

// 회원 권한 열거형
// USER : 일반 회원 (ROLE_USER)
// ADMIN : 관리자 (ROLE_ADMIN)
@Getter
public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
