package com.study.Ex19AssoMapping;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "comment")
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "content", nullable = false)
    private String content;

    // private Long boardId; // 게시글의 id - Foreign Key

    // LAZY(느림, 지연) : board 객체를 필요할 때(get) 가져온다.
    //                     Board엔티티가 준비되고 나서 가져와야함.

    // EAGER(열렬, 즉시) : Comment엔티티 생성시 가져온다.
    // 다대일 관계
    @ManyToOne(fetch = FetchType.LAZY)
    // board_id라는 FK컬럼을 만든다.
    // 내부적으로는 join 쿼리를 이용한다.
    @JoinColumn(name = "board_id")
    private Board board; // Board엔티티의 객체를 매핑한다.
}













