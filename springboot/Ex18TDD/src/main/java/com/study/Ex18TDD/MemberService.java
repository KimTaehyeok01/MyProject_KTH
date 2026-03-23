package com.study.Ex18TDD;

import org.springframework.stereotype.Service;

import java.lang.reflect.Member;

@Service
public class MemberService {
    public int loginAction(MemberDto dto){
        if(dto.getLoginId().equals("hong") && dto.getLoginPw().equals("1234")){
            return 1; // 로그인 성공
        }
        else {
            return 0; // 로그인 실패
        }
    }
}
