package com.study.LibraryStie.service;

import com.study.LibraryStie.dto.MemberRequest;
import com.study.LibraryStie.dto.MemberResponse;
import com.study.LibraryStie.domain.member.Member;
import com.study.LibraryStie.domain.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(MemberRequest dto) {
        if (memberRepository.existsByUserId(dto.getUserId())) {
            throw new IllegalArgumentException("이미 사용 중인 아이디입니다.");
        }
        if (memberRepository.existsByEmail(dto.getEmail())) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }
        Member member = Member.builder()
                .userId(dto.getUserId())
                .password(passwordEncoder.encode(dto.getPassword()))
                .userName(dto.getUserName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .userRole("ROLE_USER")
                .joinDate(dto.toSaveEntity().getJoinDate())
                .build();
        memberRepository.save(member);
    }

    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        List<Member> list = memberRepository.findAll();
        return list.stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public MemberResponse findById(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return new MemberResponse(member);
    }

    @Transactional(readOnly = true)
    public MemberResponse findByUserId(String userId) {
        Member member = memberRepository.findByUserId(userId).orElseThrow(() ->
                new IllegalArgumentException("회원을 찾을 수 없습니다."));
        return new MemberResponse(member);
    }

    @Transactional(readOnly = true)
    public String findUserNameByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .map(Member::getUserName)
                .orElse(userId);
    }

    @Transactional(readOnly = true)
    public String findEmailByUserId(String userId) {
        return memberRepository.findByUserId(userId)
                .map(Member::getEmail)
                .orElse(userId + "@library.com");
    }

    @Transactional
    public void update(Long id, MemberRequest dto) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("회원을 찾을 수 없습니다."));
        member.update(
                passwordEncoder.encode(dto.getPassword()),
                dto.getUserName(),
                dto.getEmail(),
                dto.getPhone()
        );
    }

    @Transactional
    public void delete(Long id) {
        Member member = memberRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("회원을 찾을 수 없습니다."));
        memberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public long count() {
        return memberRepository.count();
    }
}
