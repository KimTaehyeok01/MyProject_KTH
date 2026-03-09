package com.postMemo.PostMemo;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final MemoService service;

    @GetMapping("/")
    public String board(Model model) {
        List<MemoResponseDto> memos = service.findAll();
        model.addAttribute("memos", memos);
        return "app";
    }

    // 생성
    @PostMapping("/memos")
    public String addMemo(@ModelAttribute MemoRequestDto dto) {
        service.create(dto);
        return "redirect:/";
    }

    // 수정 (제목·내용·위치)
    @PostMapping("/memos/{id}")
    public Object updateMemo(@PathVariable Long id,
                             @ModelAttribute MemoRequestDto dto,
                             @RequestHeader(value = "X-Requested-With", required = false) String xhr) {
        service.update(id, dto);
        if ("XMLHttpRequest".equals(xhr)) return ResponseEntity.noContent().build();
        return "redirect:/";
    }

    // 색상 변경
    @PostMapping("/memos/{id}/color")
    public Object updateColor(@PathVariable Long id,
                              @RequestParam("color") String color,
                              @RequestHeader(value = "X-Requested-With", required = false) String xhr) {
        service.updateColor(id, color);
        if ("XMLHttpRequest".equals(xhr)) return ResponseEntity.noContent().build();
        return "redirect:/";
    }

    // 삭제
    @PostMapping("/memos/{id}/delete")
    public String deleteMemo(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }

    // 전체 삭제
    @PostMapping("/memos/clear")
    public String clearAll() {
        service.deleteAll();
        return "redirect:/";
    }
}
