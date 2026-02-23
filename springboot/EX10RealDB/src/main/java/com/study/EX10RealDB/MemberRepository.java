package com.study.EX10RealDB;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository : @Component가 들어간 DB제어 클래스
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // JpaRepository의 기본 함수
    // 1. findAll : select * SQL을 실행
    // 2. findBy 열이름 :  select 컬럼명 SQL을 실행
    // 3. save : insert, update SQL을 실행
    // 4. delete : delete SQL을 실행.
}
