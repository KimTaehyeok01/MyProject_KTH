package com.study.Ex19AssoMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// 코드로 DB에 데이터 넣기
@Component // 빈으로 등록
@RequiredArgsConstructor
// CommandLineRunner : 스프링 앱이 구동될 때, 자동으로 실행해주는 인터페이스 구현함수
public class DataInit implements CommandLineRunner {
    private final BoardRepository boardRepository;

    // String... : 가변매개변수 -> 매개변수가 없거나, 하나이거나, 여러개 이거나, 배열이여도 받아들임.
    //    예) run(), run("abc"), run("abc", "123")=> String[], run({"abc", "123"})
    @Override
    public void run(String... args) throws Exception {
        // 게시글 1 + 댓글
        Board b1 = Board.builder().title("JPA 공부").content("JPA 연관관계 매핑을 배워봅시다.").build();
        b1.getComments().add(Comment.builder().content("좋은 글이네요!").board(b1).build());
        b1.getComments().add(Comment.builder().content("잘 배워갑니다.").board(b1).build());
        b1.getComments().add(Comment.builder().content("감사합니다!").board(b1).build());

        // 게시글 2 + 댓글
        Board b2 = Board.builder().title("Spring Boot 시작").content("스프링 부트 프로젝트를 생성해봅시다.").build();
        b2.getComments().add(Comment.builder().content("따라해봤는데 잘 됩니다!").board(b2).build());
        b2.getComments().add(Comment.builder().content("초보자에게 좋은 글이에요.").board(b2).build());

        // 게시글 3 (댓글 없음)
        Board b3 = Board.builder().title("H2 데이터베이스").content("H2 인메모리 DB 사용법을 알아봅시다.").build();

        boardRepository.save(b1);
        boardRepository.save(b2);
        boardRepository.save(b3);
    }
}
