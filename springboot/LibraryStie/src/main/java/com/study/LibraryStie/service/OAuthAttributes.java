package com.study.LibraryStie.service;

import com.study.LibraryStie.domain.snsUser.SnsUser;
import com.study.LibraryStie.enumeration.UserRole;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;

@Getter
public class OAuthAttributes {

    private Map<String, Object> attributes;
    private String nameAttributeKey;
    private String registrationId;
    private String providerId;
    private String name;
    private String email;
    private String picture;

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
