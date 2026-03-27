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

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    private final HttpSession httpSession;
    private final SnsUserRepository snsUserRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {

        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        String userNameAttributeName = userRequest.getClientRegistration()
                .getProviderDetails().getUserInfoEndpoint().getUserNameAttributeName();

        OAuthAttributes attribute = OAuthAttributes.of(
                registrationId, userNameAttributeName, oAuth2User.getAttributes());

        SnsUser user = saveOrUpdate(attribute);

        httpSession.setAttribute("user", new SessionUser(user));

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

    private String resolveEmail(OAuthAttributes attr) {
        if (StringUtils.hasText(attr.getEmail())) {
            return attr.getEmail();
        }
        return attr.getRegistrationId() + "_" + attr.getProviderId() + "@no-email.local";
    }

    private String resolveName(OAuthAttributes attr, String email) {
        if (StringUtils.hasText(attr.getName())) {
            return attr.getName();
        }
        return email;
    }

    private String resolvePicture(OAuthAttributes attr) {
        if (StringUtils.hasText(attr.getPicture())) {
            return attr.getPicture();
        }
        return "";
    }
}
