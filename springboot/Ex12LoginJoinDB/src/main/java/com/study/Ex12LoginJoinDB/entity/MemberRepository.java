package com.study.Ex12LoginJoinDB.entity;

import com.study.Ex12LoginJoinDB.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

// @Repository : @Component가 들어간 DB제어 클래스
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 사용자 정의 정의
    // private String userId; -> findByUserId
    // 쿼리 자동생성 : select *from Table where user_id = "홍길동"
    Optional<MemberEntity> findByUserId(String userId);

    // JPA 커스텀 쿼리 생성하는 규칙 : 단점 - 모든 검색을 함수호출로는 불가능!
    //https://velog.io/@633jinn/JPARepository-%EB%A9%94%EC%86%8C%EB%93%9C-%EC%BB%A4%EC%8A%A4%ED%85%80%ED%95%98%EA%B8%B0

    // JPA에서 네이티브 SQL, JPQL을 사용하는 방법

    // JpaRepository의 기본 함수
    // 1. findAll : select * from table - SQL을 실행
    // 2. findBy 열이름() :  select * from table where 컬럼명=값 - SQL을 실행
    //    예) findBy((Long)2) : select * from table where id=2
    //         findByUserName("홍길동") : select * from table where username="홍길동"
    // 3. save : insert, update SQL을 실행 : id값을 보고 있으면 update, 없으면 insert
    // 4. delete : delete SQL을 실행.
}
