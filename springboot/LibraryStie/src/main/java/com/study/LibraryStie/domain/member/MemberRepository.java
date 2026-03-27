package com.study.LibraryStie.domain.member;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {

    @Query(value = "SELECT * FROM member m WHERE m.userId = :userId", nativeQuery = true)
    Optional<Member> findByUserId(@Param("userId") String userId);

    @Query(value = "SELECT * FROM member m WHERE m.email = :email", nativeQuery = true)
    Optional<Member> findByEmail(@Param("email") String email);

    boolean existsByUserId(String userId);

    boolean existsByEmail(String email);
}
