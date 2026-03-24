package com.study.Ex19AssoMapping;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class BoardContoller {
    private final BoardRepository boardRepository;

    @GetMapping("/")
    public String list(Model model){
        model.addAttribute("boards", boardRepository.findAll());
        return "boards";
    }
}
