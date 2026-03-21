package com.study.Ex17JWT.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

// @RestControllerAdvice : 모든 컨트롤러에서 발생하는 예외를 한 곳에서 처리
// 각 컨트롤러마다 try-catch 안써도 되게 해주는 전역 예외처리기
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 비밀번호 틀렸을 때 발생하는 예외 처리
    // → 401 UNAUTHORIZED 반환
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, Object>> handleBadCredentials(BadCredentialsException e) {
        return errorResponse(HttpStatus.UNAUTHORIZED, e.getMessage());
    }

    // 권한 없는 페이지 접근할 때 발생하는 예외 처리
    // → 403 FORBIDDEN 반환
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException e) {
        return errorResponse(HttpStatus.FORBIDDEN, e.getMessage());
    }

    // 존재하지 않는 유저 조회할 때 발생하는 예외 처리
    // → 404 NOT_FOUND 반환
    @ExceptionHandler(UsernameNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUsernameNotFound(UsernameNotFoundException e) {
        return errorResponse(HttpStatus.NOT_FOUND, e.getMessage());
    }

    // throw new IllegalArgumentException("메시지") 발생했을 때 처리
    // → 400 BAD_REQUEST 반환
    // 잘못된 요청값, 중복 이메일, 비밀번호 불일치 등에서 주로 사용
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException e) {
        return errorResponse(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    // 위에서 처리 못한 나머지 모든 예외 처리
    // → 500 INTERNAL_SERVER_ERROR 반환
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleEtc(Exception e) {
        return errorResponse(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
    }

    // 공통 에러 응답 형태를 만들어주는 메서드
    // 모든 에러를 아래 형태의 JSON으로 통일해서 반환
    // {
    //   "status": 400,
    //   "error": "Bad Request",
    //   "message": "비밀번호가 틀렸습니다."
    // }
    private ResponseEntity<Map<String, Object>> errorResponse(HttpStatus status, String message) {
        Map<String, Object> body = new HashMap<>();
        body.put("status", status.value());       // 상태코드 숫자 (400, 401 등)
        body.put("error", status.getReasonPhrase()); // 상태코드 이름 ("Bad Request" 등)
        body.put("message", message);              // 실제 에러 메시지
        return ResponseEntity.status(status).body(body);
    }
}
