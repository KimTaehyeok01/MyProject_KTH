package com.study.Ex18TDD;

import lombok.*;
import org.springframework.stereotype.Component;

@Component
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode // 객체를 비교할 때 사용하는 함수
public class MemberDto {
    private String loginId;
    private String loginPw;
}
