package com.notice_board.notice_board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin(origins = "*") // 모든 도메인 허용
public class BoardController {

    private final BoardRepository boardRepository;

    // 1. 목록 조회
    @GetMapping("/posts")
    public List<Board> getPosts() {
        return boardRepository.findAll();
    }

    // 2. 글 저장
    @PostMapping("/posts")
    public Board createPost(@RequestBody Board board) {
        return boardRepository.save(board);
    }

    // 3. 상세보기 & 조회수 증가
    @GetMapping("/posts/{id}")
    public Board getPost(@PathVariable Long id) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("글을 찾을 수 없습니다."));

        board.setViewCnt(board.getViewCnt() + 1);
        return boardRepository.save(board);
    }

    // 4. 글 수정
    @PutMapping("/posts/{id}")
    public Board updatePost(@PathVariable Long id, @RequestBody Board boardDetails) {
        Board board = boardRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("수정할 글이 없습니다."));

        board.setTitle(boardDetails.getTitle());
        board.setContent(boardDetails.getContent());

        return boardRepository.save(board);
    }

    // 5. 글 삭제
    @DeleteMapping("/posts/{id}")
    public void deletePost(@PathVariable Long id) {
        boardRepository.deleteById(id);
    }
}