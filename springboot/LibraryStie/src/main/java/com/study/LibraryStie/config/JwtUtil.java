package com.study.LibraryStie.config;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

// JWT 토큰 생성 / 검증 / 파싱 유틸
// 로그인 성공 -> 토큰 발급 -> 프론트 저장 -> 요청마다 헤더에 토큰 포함 -> 서버 검증 -> 인증 처리
// 겁나 어렵네...
@Component
@RequiredArgsConstructor
public class JwtUtil {

    // application-secret.properties 에서 주입
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.expiration_time}")
    private long expireTime;

    private SecretKey signingKey;

    // 스프링 빈 초기화 완료 후 시크릿 키 설정
    @PostConstruct
    protected void init() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);

        if (keyBytes.length < 32) {
            keyBytes = Arrays.copyOf(keyBytes, 32);
        }
        this.signingKey = Keys.hmacShaKeyFor(keyBytes);
    }

    // JWT 토큰 생성
    public String createToken(String userId, List<String> roleList) {
        Date now = new Date();
        return Jwts.builder()
                .subject(userId)               // 사용자 식별자
                .claim("roles", roleList)      // 권한 정보
                .issuedAt(now)                 // 발급 시간
                .expiration(new Date(now.getTime() + expireTime)) // 만료 시간
                .signWith(signingKey)          // 서명
                .compact();
    }

    // JWT 토큰에서 인증 정보 추출
    public Authentication getAuthentication(String token) {
        Claims claims = getClaims(token);
        String subject = claims.getSubject();
        java.util.List<?> rawRoles = claims.get("roles", java.util.List.class);
        java.util.List<SimpleGrantedAuthority> authorities = rawRoles == null
                ? java.util.List.of(new SimpleGrantedAuthority("ROLE_USER"))
                : rawRoles.stream().map(r -> new SimpleGrantedAuthority(r.toString())).collect(Collectors.toList());
        UserDetails userDetails = User.withUsername(subject).password("").authorities(authorities).build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }

    // HTTP 요청 헤더에서 JWT 토큰 추출
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("JWT_TOKEN");
    }

    // 토큰 유효성 검사
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            // 만료 시간이 현재 시간보다 이후이면 유효
            return claims.getExpiration().after(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    // 토큰에서 Claims 파싱
    private Claims getClaims(String token) {
        return Jwts.parser()
                .verifyWith(signingKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
