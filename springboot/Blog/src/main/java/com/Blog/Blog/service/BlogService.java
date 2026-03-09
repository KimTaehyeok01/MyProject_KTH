package com.Blog.Blog.service;

import com.Blog.Blog.dto.AddUserRequest;
import com.Blog.Blog.dto.BlogRequestDto;
import com.Blog.Blog.dto.BlogResponseDto;
import com.Blog.Blog.entity.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BlogService {

    private final BlogRepository repository;
    private final BlogUserInfoRepository blogUserInfoRepository;
    private final PasswordEncoder passwordEncoder;

    // 전체 조회
    @Transactional(readOnly = true)
    public List<BlogResponseDto> findAll(){
        List<BlogEntity> list = repository.findAll();
        return list.stream().map(BlogResponseDto :: new).collect(Collectors.toList());
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public BlogResponseDto findById(final Long id){
        BlogEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("조회 실패"));
        return new BlogResponseDto(entity);
    }

    // 로그인 체크
    @Transactional(readOnly = true)
    public BlogUserInfo loginCheck(final String userId, final String userPassword) {
        BlogUserInfo user = blogUserInfoRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!passwordEncoder.matches(userPassword, user.getUserPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }
        return user;
    }

    // 게시글 저장
    @Transactional
    public void save(final BlogRequestDto dto){
        repository.save(dto.toSaveEntity());
    }

    // 회원가입 
    @Transactional
    public void save(AddUserRequest dto) {
        BlogUserInfo userInfo = BlogUserInfo.builder()
                .userId(dto.getUserId())
                .userPassword(passwordEncoder.encode(dto.getUserPassword()))
                .userName(dto.getUserName())
                .userEmail(dto.getUserEmail())
                .build();
        blogUserInfoRepository.save(userInfo);
    }

    // 수정
    @Transactional
    public void update(final Long id, final BlogRequestDto dto){
        BlogEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("수정 실패"));
        entity.update(dto.getUserTitle(), dto.getUserContent());
    }

    // 삭제
    @Transactional
    public void delete(final Long id){
        BlogEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("삭제 실패"));
        repository.delete(entity);
    }

    // 페이징 조회
    @Transactional(readOnly = true)
    public Page<BlogResponseDto> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        Page<BlogEntity> entities = repository.findAll(pageable);
        return entities.map(entity -> new BlogResponseDto(entity));
    }

    // 검색
    @Transactional(readOnly = true)
    public List<BlogResponseDto> findByTitle(String userTitle){
        List<BlogEntity> list = repository.findByUserTitleContaining(userTitle);
        return list.stream().map(BlogResponseDto :: new).collect(Collectors.toList());
    }
}