package com.study.LibraryStie.enumeration;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("ROLE_USER"), ADMIN("ROLE_ADMIN");

    private String value;

    UserRole(String value) {
        this.value = value;
    }
}
