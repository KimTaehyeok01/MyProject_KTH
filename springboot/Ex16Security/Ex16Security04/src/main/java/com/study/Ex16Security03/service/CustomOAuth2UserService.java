package com.study.Ex16Security03.service;

import com.study.Ex16Security03.entity.SnsUser;
import com.study.Ex16Security03.entity.SnsUserRepository;
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

import java.util.Collections;

@RequiredArgsConstructor
@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
    private final HttpSession httpSession; // 세션 주입
    private final SnsUserRepository repository; // DB 저장용

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2UserService delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        // registrationId : Google, Kakao, Naver, GitHub, Apple
        String registrationId = userRequest.getClientRegistration().getRegistrationId();
        String userNameAttributeName = userRequest.getClientRegistration().getProviderDetails()
                .getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attribute = OAuthAttributes.of(registrationId, userNameAttributeName,
                oAuth2User.getAttributes());

        SnsUser user = saveOrUpdate(attribute);

        // 데이터 샐명주기
        // model, request : 요청시(request) -> 응답 떼(response)까지, 리다이렉트하면 데이터 사라짐
        // session : 로그인을 하고부터 로그아웃까지(로그인 아이디, 로그인 여부, 필수 정보 ,.. 저장 용도)
        // application : 웹을 켜고 끄기까지
        httpSession.setAttribute("user", new SessionUser(user));

        return new DefaultOAuth2User(Collections.singleton(new SimpleGrantedAuthority(user.getRoleKey())),
                attribute.getAttributes(),
                attribute.getNameAttributeKey());
    }

    private SnsUser saveOrUpdate(OAuthAttributes authAttributes) {
        SnsUser snsUser = repository.findByEmail(authAttributes.getEmail())
                .map(entity -> entity.update(authAttributes.getName(), authAttributes.getPicture()))
                .orElse(authAttributes.toEntity());
        return repository.save(snsUser);
    }
}
