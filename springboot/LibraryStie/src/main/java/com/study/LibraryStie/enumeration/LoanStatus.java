package com.study.LibraryStie.enumeration;

import lombok.Getter;

// 대출 상태 열거형
// BORROWED : 대출 중
// RETURNED : 반납 완료
@Getter
public enum LoanStatus {
    BORROWED("대출중"), RETURNED("반납완료");

    private String value;

    LoanStatus(String value) {
        this.value = value;
    }
}
