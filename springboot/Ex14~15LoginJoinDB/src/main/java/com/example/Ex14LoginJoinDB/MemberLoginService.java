package com.example.Ex14LoginJoinDB;

import com.example.Ex14LoginJoinDB.Dto.MemberRequestDto;
import com.example.Ex14LoginJoinDB.Dto.MemberResponseDto;
import com.example.Ex14LoginJoinDB.Entity.MemberEntity;
import com.example.Ex14LoginJoinDB.Entity.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemberLoginService {
    @Autowired
    private MemberRepository repository;

    // 전체 조회
    @Transactional(readOnly = true)
    public List<MemberResponseDto> findAll() {
        List<MemberEntity> list = repository.findAll();
        return list.stream().map(MemberResponseDto::new).collect(Collectors.toList());
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public MemberResponseDto findById(final Integer id) {
        MemberEntity entity = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("정보 조회 실패"));
        return new MemberResponseDto(entity);
    }

    // 로그인 확인용
    @Transactional(readOnly = true)
    public MemberResponseDto loginCheck(String userName, String password) {
        MemberEntity entity = repository.findByMemberUserName(userName)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디입니다."));

        if (!entity.getMemberPassword().equals(password)) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        return new MemberResponseDto(entity);
    }

    // 저장
    @Transactional
    public void save(MemberRequestDto dto) {
        repository.save(dto.toSaveEntity());
    }

    // 수정
    @Transactional
    public void update(final Integer id, final MemberRequestDto dto) {
        MemberEntity entity = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("수정 실패"));
        entity.update(dto.getMemberUserName(), dto.getMemberPassword(), dto.getMemberEmail(),
                dto.getMemberRole());
    }

    // 삭제
    @Transactional
    public void delete(final Integer id){
        MemberEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("삭제 실패"));
        repository.delete(entity);
    }
}













