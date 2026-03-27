package com.study.LibraryStie.enumeration;

import lombok.Getter;

@Getter
public enum LoanStatus {
    BORROWED("대출중"), RETURNED("반납완료");

    private String value;

    LoanStatus(String value) {
        this.value = value;
    }
}
