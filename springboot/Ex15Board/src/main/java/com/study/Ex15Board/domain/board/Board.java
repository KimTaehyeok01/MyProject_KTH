package com.study.Ex15Board.domain.board;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import jakarta.persistence.Id;


@Table(name = "board")
@Entity
@Getter
// 외부 패키지에서 new Board() 생성을 불가하도록 제한한다.
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
//@Builder -> 빌더패턴에서 객체를 생성하도록 유도한다.
public class Board {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="board_idx", nullable = false) // 실제 컬럼이름과 null 허용을 설정함.
    private Long board_idx; // 인덱스
    @Column(name="board_name", nullable = false)
    private String boardName; // 글쓴이
    @Column(name="board_title", nullable = false)
    private String boardTitle; // 작성자
    @Column(name="board_content", nullable = false)
    private String boardContent; // 글제목
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name="board_date", nullable = false)
    private LocalDateTime boardDate = LocalDateTime.now(); // 작성일시
    @Column(name="board_hit", nullable = false)
    private Integer boardHit; // 조회수

    @Builder // 선택적 매개변수가 있는 생성자 빌더 패턴으로 만들기
    public Board(String boardName, String boardTitle, String boardContent, Integer boardHit){
        this.boardName = boardName;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardHit = boardHit;
    }

    // 개발자가 setter대신 직접 update메서드를 만든다.
    // JPA의 엔티티 인스턴스의 필드(멤버변수)에 데이터를 set하면, 자동으로 DB테이블에 적용된다.
    public void update(String boardName, String boardTitle, String boardContent, Integer boardHit){
        this.boardName = boardName;
        this.boardTitle = boardTitle;
        this.boardContent = boardContent;
        this.boardHit = boardHit;
        this.boardDate = LocalDateTime.now();
    }

    // 조회수 업데이트
    public void updateHit(Integer boardHit){
        this.boardHit = boardHit;
    }
}
