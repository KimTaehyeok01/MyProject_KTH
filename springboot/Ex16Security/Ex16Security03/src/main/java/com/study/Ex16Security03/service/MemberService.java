package com.study.Ex16Security03.service;

import com.study.Ex16Security03.dto.MemberRequest;
import com.study.Ex16Security03.dto.MemberResponse;
import com.study.Ex16Security03.entity.Member;
import com.study.Ex16Security03.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository repository;
    private final PasswordEncoder passwordEncoder;

    // 로그인 액션은 시큐리티가 직접 처리함.
    // 회원가입 액션 직접 처리해야함.
    // 회원가입
    public boolean joinAction(MemberRequest dto) {
        // 비번은 BCrypt해야함.
        String encodedPassword = passwordEncoder.encode(dto.getPassword());
        System.out.println("rowPassword : " + dto.getPassword());
        System.out.println("encodedPassword : " + encodedPassword);
        dto.setPassword(encodedPassword); // 암호화된 비번 설정!

        try {
            repository.save(dto.toSaveEntity());
        } catch (IllegalArgumentException e) {
            e.printStackTrace(); // 예외 로그 출력
            return false; // 회원가입 실패
        }
        return true; // 회원가입 성공
    }

    // 회원가입
    @Transactional
    public void signUp(MemberRequest dto) {
        Member member = Member.builder()
                .id(dto.getId())
                .username(dto.getUsername())
                .password(passwordEncoder.encode(dto.getPassword()))
                .nickName(dto.getNickName())
                .userRole(dto.getUserRole())
                .joinDate(dto.getJoinDate())
                .build();
        try {
            repository.save(member);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    // 전체 조회
    @Transactional(readOnly = true)
    public List<MemberResponse> findAll() {
        List<Member> list = repository.findAll();
        return list.stream().map(MemberResponse::new).collect(Collectors.toList());
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public MemberResponse findById(final Long id) {
        Member member = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("조회를 할 수가 없습니다."));
        return new MemberResponse(member);
    }

    // 강사님이 만든 단건 조회
    @Transactional(readOnly = true)
    public MemberRequest getDto(Long id) {
        Optional<Member> optional = repository.findById(id); // DB id로 단건 조회 레코드 한개 변환
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다.");
            // 예외처리하는 방법 2가지
            // 1. try catch문
            // 2. 메소드를 throws Exception으로 처리
        }
        Member member = optional.get();
        return member.toUpdate();
    }

    // 업데이트
    @Transactional
    public void update(final Long id, final MemberRequest dto) {
        Member member = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("사용자를 찾을 수 없습니다."));
        member.update(dto.getUsername(), passwordEncoder.encode(dto.getPassword()),
                dto.getNickName(), dto.getJoinDate());
    }

    // 강사님이 만든 메서드
    public boolean modifyAction(MemberRequest dto) {
        try {
            repository.save(dto.toSaveEntity());
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    // 삭제
    @Transactional
    public void delete(final Long id){
        Member member = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("사용자 정보를 삭제할 수 없습니다."));
        repository.delete(member);
    }

    // 강사님이 만든 삭제 메서드
    @Transactional
    public boolean deleteDTO(final Long id){
        try {
            repository.deleteById(id);
            return true;
        }
        catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}











