package com.study.LibraryStie.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // 아이디로 회원 조회 (로그인 처리)
    @Query(value = "SELECT * FROM member m WHERE m.userId = :userId", nativeQuery = true)
    Optional<Member> findByUserId(@Param("userId") String userId);

    // 이메일로 회원 조회
    @Query(value = "SELECT * FROM member m WHERE m.email = :email", nativeQuery = true)
    Optional<Member> findByEmail(@Param("email") String email);

    // 아이디 중복 확인
    boolean existsByUserId(String userId);

    // 이메일 중복 확인
    boolean existsByEmail(String email);
}
