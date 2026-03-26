package com.study.LibraryStie.service;

import com.study.LibraryStie.domain.snsUser.SnsUser;
import com.study.LibraryStie.enumeration.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

// 각 OAuth2 플랫폼(카카오, 네이버)의 응답 속성을 통일된 형태로 변환하는 클래스
@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes; // OAuth2 원본 속성
    private String nameAttributeKey;        // 사용자 식별 키
    private String registrationId;          // 플랫폼 이름 (kakao, naver)
    private String providerId;              // 플랫폼 고유 ID
    private String name;                    // 이름
    private String email;                   // 이메일
    private String picture;                 // 프로필 이미지

    @Builder
    public OAuthAttributes(Map<String, Object> attributes, String nameAttributeKey,
                           String registrationId, String providerId,
                           String name, String email, String picture) {
        this.attributes = attributes;
        this.nameAttributeKey = nameAttributeKey;
        this.registrationId = registrationId;
        this.providerId = providerId;
        this.name = name;
        this.email = email;
        this.picture = picture;
    }

    // 플랫폼 별로 다른 구조의 응답을 통일된 OAuthAttributes 로 변환
    public static OAuthAttributes of(String registrationId,
                                     String userNameAttributeName,
                                     Map<String, Object> attributes) {
        if ("kakao".equals(registrationId)) {
            return ofKakao(registrationId, userNameAttributeName, attributes);
        } else if ("naver".equals(registrationId)) {
            return ofNaver(registrationId, userNameAttributeName, attributes);
        }
        throw new IllegalArgumentException("지원하지 않는 소셜 로그인입니다: " + registrationId);
    }

    // 카카오 응답 처리
    // {
    //   "id": 12345,
    //   "kakao_account": {
    //     "email": "user@kakao.com",
    //     "profile": { "nickname": "홍길동", "profile_image_url": "..." }
    //   }
    // }
    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofKakao(String registrationId,
                                           String userNameAttributeName,
                                           Map<String, Object> attributes) {
        Map<String, Object> kakaoAccount = (Map<String, Object>) attributes.get("kakao_account");
        Map<String, Object> profile = kakaoAccount != null
                ? (Map<String, Object>) kakaoAccount.get("profile")
                : null;

        String name = (profile != null && profile.get("nickname") != null)
                ? profile.get("nickname").toString() : "";
        String email = (kakaoAccount != null && kakaoAccount.get("email") != null)
                ? kakaoAccount.get("email").toString() : "";
        String picture = (profile != null && profile.get("profile_image_url") != null)
                ? profile.get("profile_image_url").toString() : "";
        String providerId = attributes.get("id") != null ? attributes.get("id").toString() : "";

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .attributes(attributes)
                .nameAttributeKey(userNameAttributeName)
                .registrationId(registrationId)
                .providerId(providerId)
                .build();
    }

    // 네이버 응답 처리
    // {
    //   "response": {
    //     "id": "abcdef",
    //     "name": "홍길동",
    //     "email": "user@naver.com",
    //     "profile_image": "..."
    //   }
    // }
    @SuppressWarnings("unchecked")
    private static OAuthAttributes ofNaver(String registrationId,
                                           String userNameAttributeName,
                                           Map<String, Object> attributes) {
        Map<String, Object> response = (Map<String, Object>) attributes.get("response");

        String name = (response != null && response.get("name") != null)
                ? response.get("name").toString() : "";
        String email = (response != null && response.get("email") != null)
                ? response.get("email").toString() : "";
        String picture = (response != null && response.get("profile_image") != null)
                ? response.get("profile_image").toString() : "";
        String providerId = (response != null && response.get("id") != null)
                ? response.get("id").toString() : "";

        return OAuthAttributes.builder()
                .name(name)
                .email(email)
                .picture(picture)
                .attributes(response != null ? response : attributes)
                .nameAttributeKey("id")
                .registrationId(registrationId)
                .providerId(providerId)
                .build();
    }

    // 신규 SNS 사용자 엔티티 생성
    public SnsUser toEntity() {
        return SnsUser.builder()
                .name(name)
                .email(email != null ? email : "")
                .picture(picture != null ? picture : "")
                .provider(registrationId)
                .providerId(providerId)
                .userRole(UserRole.USER)
                .build();
    }
}
