package com.study.Ex14RestAPI;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ReqDto {
    private String username; // input태그의 name속성과 매칭
    private String password;
}
