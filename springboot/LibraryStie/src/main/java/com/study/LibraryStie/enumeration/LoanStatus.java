package com.study.LibraryStie.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public enum LoanStatus {
    BORROWED("대출중"), RETURNED("반납완료");

    private String value;
}
