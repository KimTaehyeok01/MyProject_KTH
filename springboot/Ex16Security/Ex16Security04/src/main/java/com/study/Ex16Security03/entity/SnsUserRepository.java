package com.study.Ex16Security03.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {
    // @Query(value = "select u from SnsUser u where u.email = :email")
    // @Query(value = "select * from sns_user where email = :email")

    // SNS 로그인으로 반환되는 값 중에서 email을 통해
    // 이미 가입돤 유저인지, 처음 가입하는 유저인지 구분하는 쿼리 메서드가 필요.
    Optional<SnsUser> findByEmail(String email);
}
