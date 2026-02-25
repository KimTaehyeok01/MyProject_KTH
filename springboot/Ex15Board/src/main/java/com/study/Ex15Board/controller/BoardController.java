package com.study.Ex15Board.controller;

import com.study.Ex15Board.domain.board.Board;
import com.study.Ex15Board.domain.board.BoardRepository;
import com.study.Ex15Board.domain.reply.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BoardController {
    private final BoardRepository boardRepository;
    @GetMapping("/")
    public String main(Model model){
        List<Board> list = boardRepository.findAll();
        model.addAttribute("list", list);
        return "listForm";
    }
}
