package com.study.Ex19AssoMapping;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "board")
@Getter @Setter
@NoArgsConstructor // 기본 생성자 - 스프링 빈 생성(Jackson Lib)오류
@AllArgsConstructor
@Builder
public class Board {
    @Id
    // IDENTITY 옵션 : mysql, h2 DB 사용시 써야함
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    // mappedBy = "board" : 연관관계 주인은 내가 아니다. comment엔티티가 주인이다.
    // Cascade : Board에 일어나는 일이 Comment에도 전파되도록 한다.
    //             게시글이 지워지면, 댓글도 지워진다.
    // orphanRemoval : 부모(Board)와 관계가 끊어지면, 자식(Comment) 자동 삭제
    // 일대다 관계
    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    // 빌더 패턴을 사용할 때, comments이 null이 아니고 빈 리스트를 유지하도록 해줌.
    private List<Comment> comments = new ArrayList<>();
}
// 전에는 댓글 조회하려면, 게시글의 id를 fk필드로 가진 레코드들을 검색했다.
// select * from comment where board_id = 3;
