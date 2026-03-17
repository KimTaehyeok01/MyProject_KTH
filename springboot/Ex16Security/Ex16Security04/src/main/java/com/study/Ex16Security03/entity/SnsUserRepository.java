package com.study.Ex16Security03.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {
    // @Query(value = "select u from SnsUser u where u.email = :email")
    // @Query(value = "select * from sns_user where email = :email")

    // provider + providerId 기준으로 같은 SNS 계정인지 구분한다.
    Optional<SnsUser> findByProviderAndProviderId(String provider, String providerId);
}
