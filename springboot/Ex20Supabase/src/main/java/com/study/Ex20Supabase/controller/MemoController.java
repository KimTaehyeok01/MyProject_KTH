package com.study.Ex20Supabase.controller;

import com.study.Ex20Supabase.entity.Memo;
import com.study.Ex20Supabase.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/memo")
public class MemoController {

    private final MemoRepository memoRepository;

    // 목록
    @GetMapping
    public String list(Model model) {
        model.addAttribute("memos", memoRepository.findAll());
        return "memo/list";
    }

    // 작성 폼
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("memo", new Memo());
        return "memo/form";
    }

    // 저장
    @PostMapping
    public String save(@ModelAttribute Memo memo) {
        memoRepository.save(memo);
        return "redirect:/memo";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id) {
        memoRepository.deleteById(id);
        return "redirect:/memo";
    }
}
