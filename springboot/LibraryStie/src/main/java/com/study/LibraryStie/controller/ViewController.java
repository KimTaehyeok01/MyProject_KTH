package com.study.LibraryStie.controller;

import com.study.LibraryStie.service.BookService;
import com.study.LibraryStie.service.LoanService;
import com.study.LibraryStie.service.MemberService;
import com.study.LibraryStie.service.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.study.LibraryStie.dto.BookResponse;

@Controller
@RequiredArgsConstructor
public class ViewController {

    private final BookService bookService;
    private final LoanService loanService;
    private final MemberService memberService;

    @GetMapping("/")
    public String main(@RequestParam(defaultValue = "") String keyword,
                       @RequestParam(defaultValue = "") String category,
                       @RequestParam(defaultValue = "0") int page,
                       Model model, HttpSession session) {

        Page<BookResponse> paging;
        if (!category.isEmpty()) {
            paging = bookService.findByCategory(category, page);
        } else if (!keyword.isEmpty()) {
            paging = bookService.search(keyword, page);
        } else {
            paging = bookService.getList(page);
        }

        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("category", category);
        model.addAttribute("totalBooks", bookService.count());

        return "index";
    }

    @GetMapping("/mypage")
    public String mypage(Model model, Authentication auth, HttpSession session) {
        String userEmail = getCurrentUserEmail(auth, session);
        String userName = getCurrentUserName(auth, session);

        model.addAttribute("activeLoans", loanService.findMyActiveLoans(userEmail));
        model.addAttribute("allLoans", loanService.findMyAllLoans(userEmail));
        model.addAttribute("userName", userName);
        model.addAttribute("userEmail", userEmail);

        return "mypage";
    }

    @GetMapping("/loan")
    public String loanPage(@RequestParam(defaultValue = "") String keyword,
                           @RequestParam(defaultValue = "0") int page,
                           Model model, Authentication auth, HttpSession session) {

        Page<BookResponse> paging;
        if (!keyword.isEmpty()) {
            paging = bookService.search(keyword, page);
        } else {
            paging = bookService.getList(page);
        }

        String userEmail = getCurrentUserEmail(auth, session);

        model.addAttribute("paging", paging);
        model.addAttribute("keyword", keyword);
        model.addAttribute("activeLoans", loanService.findMyActiveLoans(userEmail));
        model.addAttribute("userEmail", userEmail);

        return "loan";
    }

    public String getCurrentUserEmail(Authentication auth, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if (sessionUser != null && sessionUser.getEmail() != null) {
            return sessionUser.getEmail();
        }
        String sessionEmail = (String) session.getAttribute("userEmail");
        if (sessionEmail != null) {
            return sessionEmail;
        }
        if (auth != null && auth.isAuthenticated()) {
            return memberService.findEmailByUserId(auth.getName());
        }
        return "anonymous@library.com";
    }

    public String getCurrentUserName(Authentication auth, HttpSession session) {
        SessionUser sessionUser = (SessionUser) session.getAttribute("user");
        if (sessionUser != null) {
            return sessionUser.getName();
        }
        String sessionName = (String) session.getAttribute("userName");
        if (sessionName != null) {
            return sessionName;
        }
        if (auth != null) {
            return memberService.findUserNameByUserId(auth.getName());
        }
        return "사용자";
    }
}
