package com.study.Ex12LoginJoinDB.dto;

// 스프링에서 지원하는 Validation(유효성 체크) 어노테이션들
//@NotNull    Null 불가
//@Null      Null만 입력 가능
//@NotEmpty    Null, 빈 문자열 불가
//@NotBlank    Null, 빈 문자열, 스페이스만 있는 문자열 불가
//@Size(min=,max=)    문자열, 배열등의 크기가 만족하는가?
//@Pattern(regex=)    정규식을 만족하는가?
//@Max(숫자)    지정 값 이하인가?
//@Min(숫자)    지정 값 이상인가
//@Future    현재 보다 미래인가?
//@Past    현재 보다 과거인가?
//@Positive    양수만 가능
//@PositiveOrZero    양수와 0만 가능
//@Negative    음수만 가능
//@NegativeOrZero    음수와 0만 가능
//@Email    이메일 형식만 가능
//@Digits(integer=, fraction = )    대상 수가 지정된 정수와 소수 자리 수 보다 작은가?
//@DecimalMax(value=)     지정된 값(실수) 이하인가?
//@DecimalMin(value=)    지정된 값(실수) 이상인가?
//@AssertFalse    false 인가?
//@AssertTrue     true 인가?

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MemberLoginDto {
    @NotBlank(message = "userId에 null, 빈 문자열, 스페이스문자만 넣을 수 없음.")
    @Size(min = 4, max = 20)
    private String userId;

    @NotBlank(message = "userPw에 null, 빈 문자열, 스페이스문자만 넣을 수 없음.")
    @Size(min = 4, max = 20)
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*\\d)[a-z\\d]{8,}$",
            message = "비밀번호는 8자 이상이며, 영문 소문자와 숫자를 모두 포함해야 합니다."
    )
    private String userPw;
}