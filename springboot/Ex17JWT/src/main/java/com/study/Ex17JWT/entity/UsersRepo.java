package com.study.Ex17JWT.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsersRepo extends JpaRepository<Users, Long> {
  //1. 기본함수 : findAll, findById, save, count, deleteById
  //2. 사용자정의함수 : 열이름, DB부사절(where, or, and, orderby, desc, asc, limit, groupby)
  //                   김수한무... 이름이 너무 길어지는 단점.
  // SQL Query : select * from users_jwt where email=:email
  Optional<Users> findByEmail(String email);
  // SQL QUery : select * from users_jwt where email=:email and password=:password
  Optional<Users> findByEmailAndPassword(String email, String password);
  //3. @Query : native Query(AnsiSQL), JPQL(엔티티)

}
