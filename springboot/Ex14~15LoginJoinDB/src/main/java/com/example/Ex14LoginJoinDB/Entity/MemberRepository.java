package com.example.Ex14LoginJoinDB.Entity;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Integer> {
    Optional<MemberEntity> findByMemberUserName(String memberUserName);
}
