package com.study.Ex15Board.controller;

import com.study.Ex15Board.dto.BoardResponseDto;
import com.study.Ex15Board.dto.BoardSaveRequestDto;
import com.study.Ex15Board.dto.ReplyResponseDto;
import com.study.Ex15Board.service.BoardService;
import com.study.Ex15Board.service.ReplyService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board") // 시작하는 URL을 "/board"로 지정한다.
public class BoardController {

    private final BoardService boardService;
    private final ReplyService replyService;

    // URL : http://localhost:8080/board
    @GetMapping("/")
    public String board() {
        return "redirect:/board/listForm";
    }

    @GetMapping("listForm")
    public String listForm(Model model) {
        List<BoardResponseDto> list = boardService.findAll();
        model.addAttribute("list", list);
        return "listForm";
    }

    @GetMapping("/writeForm")
    public String writeForm() {
        return "writeForm";
    }

    // HTTP 요청에서 보내주는 데이터 2가지
    // 1. Parameter Key-Value Date : @RequestParam
    // 2. Body Raw Data : @RequestBody @ModelAttribute(클래스나 Map에 매핑)

    // 스프링(자바) 응답할 때
    // 1. @ResponseBody : HTTP Body Raw Data(Json/xml/js)
    // 2. HTML 파일 응답 : View Resolver에서 알아서 보낸다.
    @PostMapping("/writeAction")
    @ResponseBody
    public String writeAction(@ModelAttribute BoardSaveRequestDto dto) {
        Long newIdx = boardService.save(dto);
        boolean isFound = boardService.existsById(newIdx);

        if (isFound) {
            return "<script>alert('글쓰기 성공'); location.href='/board/listForm';</script>";
        } else {
            // 글쓰기(로그인,회원가입)시에 히스토리 백하면, 유저가 입력한 내용이 그대로 있다.
            return "<script>alert('글쓰기 실패'); history.back();</script>";
        }
    }

    @GetMapping("/contentForm")
    // URL : /board/contentForm?boardIdx=3
    public String contentForm(@RequestParam Long boardIdx, Model model) {
        // 게시글 단건 조회
        BoardResponseDto dto = boardService.findById(boardIdx);

        // 조회수 증가
        boardService.updateHit(boardIdx, dto.getBoardHit() + 1);
        // 임시로 dto에
        dto.setBoardHit(dto.getBoardHit() + 1);

        // 댓글 목록 조회
        List<ReplyResponseDto> replyList = replyService.findAllByReplyBoardIdx(boardIdx);
        model.addAttribute("replyList", replyList);

        model.addAttribute("dto", dto);
        return "/contentForm";
    }

    // 게시글 삭제하기
    @GetMapping("/deleteAction")
    @ResponseBody
    public String deleteAction(@RequestParam Long boardIdx) {
        try {
            boardService.delete(boardIdx);
            return "<script>alert('글삭제 성공'); location.href='/board/listForm';</script>";
        } catch (Exception e) {
            e.printStackTrace();
            return "<script>alert('글삭제 실패'); location.href='/board/listForm';</script>";
        }
    }

    // 게시글 수정
    @PostMapping("updateAction")
    @ResponseBody
    public String updateAction(@ModelAttribute BoardSaveRequestDto dto, @RequestParam Long boardIdx) {
        boolean isOk = boardService.update(boardIdx, dto);

        if (isOk) {
            return "<script>alert('글수정 성공'); location.href='/board/listForm';</script>";
        } else {
            return "<script>alert('글수정 실패'); history.back();</script>";
        }
    }
}
