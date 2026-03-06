package com.example.BookEx6.controller;

import com.example.BookEx6.domain.Article;
import com.example.BookEx6.dto.AddArticleRequest;
import com.example.BookEx6.dto.AddArticleResponse;
import com.example.BookEx6.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class BlogApiController {
    private final BlogService service;

    @GetMapping("/")
    public String list(Model model, @RequestParam(value = "page", defaultValue = "0") int page) {
        Page<Article> paging = service.getList(page);
        model.addAttribute("paging", paging);
        model.addAttribute("articles", paging.getContent());
        return "list";
    }

    // 글 작성 페이지 이동
    @GetMapping("/articles/new")
    public String addForm(Model model, AddArticleRequest request) {
        model.addAttribute("article", request);
        return "addForm";
    }

    // 글 작성
    @PostMapping("/articles")
    public String articlesAddWrite(@ModelAttribute AddArticleRequest request) {
        service.save(request);
        return "redirect:/";
    }

    // 상세페이지 이동
    @GetMapping("/articles/{id}")
    public String detailPage(@PathVariable Long id, Model model) {
        AddArticleResponse response = service.findById(id);
        model.addAttribute("article", response);
        return "detail";
    }

    // 검색
    @GetMapping("/articles/search")
    public String search(@RequestParam String title, Model model) {
        try {
            List<AddArticleResponse> result = service.findByTitle(title);
            model.addAttribute("articles", result);
            model.addAttribute("paging", Page.empty());
        } catch (IllegalArgumentException e) {
            model.addAttribute("articles", List.of());
            model.addAttribute("paging", Page.empty());
        }
        return "list";
    }

    // 수정 페이지 이동
    @GetMapping("/articles/{id}/edit")
    public String articlesEdit(@PathVariable Long id, Model model) {
        model.addAttribute("article", service.findById(id));
        return "edit";
    }

    // 수정하기
    @PostMapping("articles/{id}/edit")
    public String articlesEditSave(@PathVariable Long id, @ModelAttribute AddArticleRequest request) {
        service.update(id, request);
        return "redirect:/";
    }

    // 삭제하기
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id) {
        service.delete(id);
        return "redirect:/";
    }
}
