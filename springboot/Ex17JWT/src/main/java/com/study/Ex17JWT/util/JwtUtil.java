package com.study.Ex17JWT.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;
import java.util.List;

@Component
// @RequiredArgsConstructor 제거 → @Lazy랑 같이 못 씀
public class JwtUtil {
    //@Value : properties 또는 yml의 설정정보를 변수에 설정한다.
    @Value("${jwt.secretKey}") //토큰 시크릿키(암호화할때 사용하는 비공개키)
    private String secretKey;
    @Value("${jwt.expiration_time}") //토큰 유효 기간 ms단위
    private long expireTime;
    private SecretKey signingKey;

    private final UserDetailsService userDetailsService;

    // @Lazy → 순환참조 해결용 생성자 직접 작성
    public JwtUtil(@Lazy UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    //@PostConstruct : 스프링 빈의 생명주기(생성,소멸,활성화)
    // : 의존주입이 이루어진 후에 초기화를 스프링프레임워크가 수행해 주는 메소드에 붙임.
    @PostConstruct
    protected void init() {
        secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        signingKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKey));
    }

    public String createToken(String userEmail, List<String> roleList) {
        Claims claims = Jwts.claims()
                .subject(userEmail)
                .add("roles", roleList).build();
        Date now = new Date();
        return Jwts.builder()
                .claims(claims) //정보 저장
                .issuedAt(now) //토큰 발행 시간 저장
                .expiration(new Date(now.getTime() + expireTime))//토큰 유효기간 설정
                //암호화 알고리즘과 signature에 들어가 secret값 설정
                .signWith(signingKey)
                .compact();
    }

    //JWT 토큰에서 인증 정보 조회
    public Authentication getAuthentication(String token) {
        String email = Jwts.parser().verifyWith(signingKey).build()
                .parseSignedClaims(token).getPayload().getSubject();
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        return new UsernamePasswordAuthenticationToken(
                userDetails, "", userDetails.getAuthorities());
    }

    //Request의 Header에서 token값을 가져옵니다. "JWT_TOKEN" : "TOKEN값"
    public String resolveToken(HttpServletRequest request) {
        return request.getHeader("JWT_TOKEN");
    }

    //토큰의 유효성 + 만료일자 확인
    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().verifyWith(signingKey).build()
                    .parseSignedClaims(token);
            return !claims.getPayload().getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }
}