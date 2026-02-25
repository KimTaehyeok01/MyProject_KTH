package com.study.Ex14RestAPI;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResDto {
    // {"status" : "ok" , "message" : "로그인됨"}
    private String status;
    private String message;
}
