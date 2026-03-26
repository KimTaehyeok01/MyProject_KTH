package com.study.LibraryStie.domain.snsUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnsUserRepository extends JpaRepository<SnsUser, Long> {

    Optional<SnsUser> findByProviderAndProviderId(String provider, String providerId);

    Optional<SnsUser> findByEmail(String email);
}
