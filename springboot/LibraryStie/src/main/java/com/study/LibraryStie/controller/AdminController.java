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

// 관리자 전용 컨트롤러
@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final MemberService memberService;
    private final BookService bookService;
    private final LoanService loanService;

    // 관리자 대시보드
    @GetMapping
    public String adminMain(Model model) {
        model.addAttribute("totalMembers", memberService.count());
        model.addAttribute("totalBooks", bookService.count());
        model.addAttribute("activeLoans", loanService.countActiveLoan());
        return "admin/index";
    }

    // 회원 목록 페이지
    @GetMapping("/members")
    public String memberList(Model model) {
        model.addAttribute("memberList", memberService.findAll());
        model.addAttribute("totalCount", memberService.count());
        return "admin/members";
    }

    // 회원 강제 탈퇴 (POST)
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

    // 대출 현황 페이지
    @GetMapping("/loans")
    public String loanList(Model model) {
        model.addAttribute("loanList", loanService.findAll());
        model.addAttribute("activeCount", loanService.countActiveLoan());
        return "admin/loans";
    }

    // 도서 관리 페이지
    @GetMapping("/books")
    public String bookList(Model model) {
        model.addAttribute("bookList", bookService.findAll());
        model.addAttribute("totalCount", bookService.count());
        return "admin/books";
    }

    // 도서 등록 처리
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

    // 도서 수정 처리
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

    // 도서 삭제 처리
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
