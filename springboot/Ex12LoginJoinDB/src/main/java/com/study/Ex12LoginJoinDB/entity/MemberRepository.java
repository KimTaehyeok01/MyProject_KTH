package com.study.Ex12LoginJoinDB.entity;

import com.study.Ex12LoginJoinDB.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// @Repository : @Component가 들어간 DB제어 클래스
@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // JpaRepository의 기본 함수
    // 1. findAll : select * from table - SQL을 실행
    // 2. findBy 열이름() :  select * from table where 컬럼명=값 - SQL을 실행
    //    예) findBy((Long)2) : select * from table where id=2
    //         findByUserName("홍길동") : select * from table where username="홍길동"
    // 3. save : insert, update SQL을 실행 : id값을 보고 있으면 update, 없으면 insert
    // 4. delete : delete SQL을 실행.
}
