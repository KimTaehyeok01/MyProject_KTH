package com.study.LibraryStie.service;

import com.study.LibraryStie.domain.snsUser.SnsUser;
import com.study.LibraryStie.domain.snsUser.SnsUserRepository;
import com.study.LibraryStie.enumeration.UserRole;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;

// 카카오/네이버 소셜 로그인 처리 서비스
@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final SnsUserRepository snsUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        // 기본 OAuth2 서비스로 플랫폼에서 사용자 정보 가져오기
        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // 어떤 플랫폼인지 확인 (kakao, naver)
        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        // 각 플랫폼의 사용자 식별 키 이름 가져오기
        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        // 플랫폼별 응답 구조를 통일된 형태로 변환
        OAuthAttributes attribute = OAuthAttributes.of(
                registrationId, userNameAttributeName, oAuth2User.getAttributes());

        // DB에 저장 또는 업데이트 (신규 회원 가입 / 기존 회원 정보 갱신)
        SnsUser user = saveOrUpdate(attribute);

        // 세션에 사용자 정보 저장 (엔티티 직접 저장 금지 -> SessionUser DTO 사용)
        httpSession.setAttribute("user", new SessionUser(user));

        // Spring Security 인증 객체 반환
        return new DefaultOAuth2User(
                Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attribute.getAttributes(),
                attribute.getNameAttributeKey()
        );
    }

    private SnsUser saveOrUpdate(OAuthAttributes attr) {
        String provider = attr.getRegistrationId();
        String providerId = attr.getProviderId();
        String email = resolveEmail(attr);
        String name = resolveName(attr, email);
        String picture = resolvePicture(attr);

        SnsUser snsUser = snsUserRepository.findByProviderAndProviderId(provider, providerId)
                .map(entity -> entity.update(name, picture, email))
                .orElse(SnsUser.builder()
                        .name(name)
                        .email(email)
                        .picture(picture)
                        .provider(provider)
                        .providerId(providerId)
                        .userRole(UserRole.USER)
                        .build());

        return snsUserRepository.save(snsUser);
    }

    // 이메일이 없을 경우 대체 이메일 생성
    private String resolveEmail(OAuthAttributes attr) {
        if (StringUtils.hasText(attr.getEmail())) {
            return attr.getEmail();
        }
        return attr.getRegistrationId() + "_" + attr.getProviderId() + "@no-email.local";
    }

    // 이름이 없을 경우 이메일로 대체
    private String resolveName(OAuthAttributes attr, String email) {
        if (StringUtils.hasText(attr.getName())) {
            return attr.getName();
        }
        return email;
    }

    // 프로필 사진이 없을 경우 빈 문자열
    private String resolvePicture(OAuthAttributes attr) {
        if (StringUtils.hasText(attr.getPicture())) {
            return attr.getPicture();
        }
        return "";
    }
}
