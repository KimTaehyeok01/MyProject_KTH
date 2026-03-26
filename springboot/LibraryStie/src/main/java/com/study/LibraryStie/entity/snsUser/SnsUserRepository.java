package com.study.LibraryStie.entity.snsUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {

    // provider + providerId 로 SNS 회원 조회 (같은 SNS 계정 구분)
    Optional<SnsUser> findByProviderAndProviderId(String provider, String providerId);

    // 이메일로 SNS 회원 조회
    Optional<SnsUser> findByEmail(String email);
}
