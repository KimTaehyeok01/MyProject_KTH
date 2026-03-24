package com.study.Ex19AssoMapping;

import jakarta.persistence.*;
import lombok.*;

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
}
