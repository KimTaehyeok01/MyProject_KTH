package com.study.Ex12LoginJoinDB.entity;

import com.study.Ex12LoginJoinDB.dto.MemberSaveDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity // 엔티티 클래시임을 알려줌. DB테이블과 1:1 매핑되는 클래스.
@Table(name = "member")
@Getter
// @Setter : 넣지 않는다. 개발자의 실수나 자동으로 호출되는 경우를 제거
@Builder
@NoArgsConstructor // 기본 생성자는 필수. @ModelAttribute @RequestBody에 필요!
@AllArgsConstructor
public class MemberEntity {
    // @Id : 기본키 id열로 사용한다는 의미
    // GeneratedValue : id값을 어떻게 생성할지 전략을 선택
    // 1. IDENTITY : MySQL, MariaDB, PostgreSQL, H2DB
    // 2. SEQUENCE : Oracle, PostgreSQL
    // 3. AUTO : 자동으로 선택
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // -> JPA에서 엔티티의 기본 키(PK) 생성을 데이터베이스의 자동 증가 기능(예: MySQL의 AUTO_INCREMENT)에 위임하는 설정
    private Long id;
    private String userId; // DB는 스네이크케이스(_), 자바에선 카멜케이스 선호(userId)
    private String userPw;
    private String userName;
    private String userRole;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    // Entity -> DTO
    public MemberSaveDto toSaveDto(){
        return MemberSaveDto.builder()
                .id(id)
                .userId(userId)
                .userPw(userPw)
                .userName(userName)
                .userRole(userRole)
                .joinDate(joinDate)
                .build();
    }
}








