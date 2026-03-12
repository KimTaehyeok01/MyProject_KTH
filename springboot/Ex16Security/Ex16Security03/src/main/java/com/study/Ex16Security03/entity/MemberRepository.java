package com.study.Ex16Security03.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    // 기본함수 지원 - findAll, findById, save, update, delete, deleteById, count
    // 사용자 정의 함수 별도 기업
    // @Query를 이용하여 SQL을 직접 사용하기 : Native Query, JPQL

    // Native Query를 이용한 사용자 정의 함수
    @Query(value = "select * from member_security m where m.username=:username_param", nativeQuery = true)
    Optional<Member> findByUserName(@Param("username_param") String username);

    // JPQL을 이용한 사용자 정의 함수
    // JPQL은 select를 조회할 때, 네이티브 쿼리와 다르게 테이블 이름 대신 엔티티 이름을 쓴다!
//    @Query(value = "select m from Member m where m.username=:username_param")
//    Optional<Member> findByUserNameJPQL(@Param("username_param") String username);
}
