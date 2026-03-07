package com.Blog.Blog.login;

import com.Blog.Blog.entity.BlogUserInfo;
import com.Blog.Blog.entity.BlogUserInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final BlogUserInfoRepository blogUserInfoRepository;

    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        BlogUserInfo user = blogUserInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 아이디: " + userId));

        return new User(
                user.getUserId(),
                user.getUserPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_USER"))
        );
    }
}
