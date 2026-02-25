package com.study.Ex12LoginJoinDB.service;

import com.study.Ex12LoginJoinDB.entity.MemberEntity;
import com.study.Ex12LoginJoinDB.entity.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

// @Component가 붙은 3가지 어노테이션
// 1. @Controller : Http 요쳥을 처리하는 프론트 컨트롤러
// 2. @Repository : DB처리를 담당하는 클래스(JPA)
// 3. @Service : 로직(코드)를 주로 담당하는 클래스

@Service
@RequiredArgsConstructor
public class LoginService {
    @Autowired
    MemberRepository memberRepository;

    public String getUserRole(String userId){
        Optional<MemberEntity> optional = memberRepository.findByUserId(userId);

        if(!optional.isPresent()){
            return null;
        }

        // .get() : Optional에 래핑되어진 값을 꺼내온다.
        return optional.get().getUserRole();
    }
}
