package com.study.Ex17JWT.repository;

import com.study.Ex17JWT.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepository extends JpaRepository<Users, Long> {
    // 1. 기본함수 : findAll, findById, save, delete, deleteById, count
    // 2. 사용자 정의 함수 : 열 이름, DB부사절(where, or, and, orderby, desc, asc, limit, group by)
    // 3. @Query(JPQL(엔티티), Native Query(db테이블) )

    // @Query(value = "select * from users_jwt where email =: email")
    Optional<Users> findByEmail(String email);

    // @Query(value = "select u from Users u where u.email = :email and u.password = :password")
    Optional<Users> findByEmailAndPassword(String email, String password);
}
