package com.study.LibraryStie.controller;

import com.study.LibraryStie.dto.BookRequest;
import com.study.LibraryStie.service.BookService;
import com.study.LibraryStie.service.LoanService;
import com.study.LibraryStie.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final BookService bookService;
    private final LoanService loanService;

    @GetMapping
    public String adminMain(Model model) {
        model.addAttribute("totalMembers", memberService.count());
        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("activeLoans", loanService.countActiveLoan());
        return "admin/index";
    }

    @GetMapping("/members")
    public String memberList(Model model) {
        model.addAttribute("memberList", memberService.findAll());
        model.addAttribute("totalCount", memberService.count());
        return "admin/members";
    }

    @PostMapping("/members/delete")
    @ResponseBody
    public String deleteMember(@RequestParam Long id) {
        try {
            memberService.delete(id);
            return "<script>alert('회원이 탈퇴 처리되었습니다.'); location.href='/admin/members';</script>";
        } catch (Exception e) {
            return "<script>alert('탈퇴 처리 중 오류가 발생했습니다.'); history.back();</script>";
        }
    }

    @GetMapping("/loans")
    public String loanList(Model model) {
        model.addAttribute("loanList", loanService.findAll());
        model.addAttribute("activeCount", loanService.countActiveLoan());
        return "admin/loans";
    }

    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("bookList", bookService.findAll());
        model.addAttribute("totalCount", bookService.count());
        return "admin/books";
    }

    @PostMapping("/books/save")
    @ResponseBody
    public String saveBook(@Valid @ModelAttribute BookRequest dto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            String message = bindingResult.getFieldError().getDefaultMessage();
            return "<script>alert('입력 오류: " + message + "'); history.back();</script>";
        }
        try {
            bookService.save(dto);
            return "<script>alert('도서가 등록되었습니다.'); location.href='/admin/books';</script>";
        } catch (Exception e) {
            return "<script>alert('도서 등록 중 오류가 발생했습니다.'); history.back();</script>";
        }
    }

    @PostMapping("/books/update")
    @ResponseBody
    public String updateBook(@RequestParam Long id, @ModelAttribute BookRequest dto) {
        try {
            bookService.update(id, dto);
            return "<script>alert('도서 정보가 수정되었습니다.'); location.href='/admin/books';</script>";
        } catch (Exception e) {
            return "<script>alert('도서 수정 중 오류가 발생했습니다.'); history.back();</script>";
        }
    }

    @PostMapping("/books/delete")
    @ResponseBody
    public String deleteBook(@RequestParam Long id) {
        try {
            bookService.delete(id);
            return "<script>alert('도서가 삭제되었습니다.'); location.href='/admin/books';</script>";
        } catch (Exception e) {
            return "<script>alert('도서 삭제 중 오류가 발생했습니다.'); history.back();</script>";
        }
    }
}
