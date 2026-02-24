package com.study.Ex12LoginJoinDB.dto;

// 데이터 모델링 클래스(데이터를 담는 클래스)의 종류
// 1. DTO 클래스 : Data Transfer Object의 약자. 다른 계층간의 데이터 전송시 사용.
//              : 데이터가 자주 바뀔때 사용.
// 2. VO 클래스 :  View Object의 약자. 자주 데이터가 바뀌지 않을 때 보관용으로 사용.
// 3. Entity 클래스 : JPA에서 DB테이블과 1:1로 매핑되는 클래스. 엔티티 인스턴스에
//                     Setter를 통해 값을 바꾸면, 자동으로 테이블에 적용된다.
// MemberSaveDto : 엔티티와 다른 계층간에 중간단계의 클래스로서, 데이터 처리 용도임.
//               : 엔티티 클래스와 1:1 ORM 매핑이 가능함. 필드 이름이 동일해야함.

import com.study.Ex12LoginJoinDB.entity.MemberEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder // Builder 패턴 사용
public class MemberSaveDto {
    private Long id;
    private String userId;
    private String userPw;
    private String userName;
    private String userRole;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate joinDate;

    // DTO -> Entity 변환 함수
    public MemberEntity toSaveEntity() {

        // 객체를 생성할 때, 방법이 2가지
        // 1. new 필드가 있는 생성자
        // 2. builder()를 사용 -> 사용하면 좋은 점.
        //  1) 생성자 함수는 매개변수의 개수와 순서가 동일해야함.
        // return new MemberEntity(id, userId, userPw, userName, userRole, joinDate);
        //  -> 필드(매개변수)의 순서와 갯수를 자유롭게 하여 생성 가능하다.
        return MemberEntity.builder()
                .id(id)
                .userId(userId)
                .userRole(userRole)
                .userPw(userPw)
                .userName(userName)
                .joinDate(joinDate)
                .build();
    }

    // Entity -> DTO 상태 업데이트 함수
    public void updateDto(MemberEntity entity) {
        // id는 DB값이기에(Key) 업데이트 하지 않음.
        this.setUserId(entity.getUserId());
        this.setUserPw(entity.getUserPw());
        this.setUserName(entity.getUserName());
        this.setUserRole(entity.getUserRole());
    }
}















