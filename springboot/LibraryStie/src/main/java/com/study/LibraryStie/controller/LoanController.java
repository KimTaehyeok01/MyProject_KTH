package com.study.LibraryStie.controller;

import com.study.LibraryStie.service.LoanService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loan")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;
    private final ViewController viewController;

    @PostMapping("/{bookId}")
    public String loanBook(@PathVariable Long bookId,
                           Authentication auth, HttpSession session) {
        try {
            String userEmail = viewController.getCurrentUserEmail(auth, session);
            String userName = viewController.getCurrentUserName(auth, session);
            loanService.loanBook(bookId, userEmail, userName);
            return "SUCCESS";
        } catch (IllegalArgumentException e) {
            return "ERROR:" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR:대출 처리 중 오류가 발생했습니다.";
        }
    }

    @PostMapping("/return/{loanId}")
    public String returnBook(@PathVariable Long loanId,
                             Authentication auth, HttpSession session) {
        try {
            String userEmail = viewController.getCurrentUserEmail(auth, session);
            loanService.returnBook(loanId, userEmail);
            return "SUCCESS";
        } catch (IllegalArgumentException e) {
            return "ERROR:" + e.getMessage();
        } catch (Exception e) {
            e.printStackTrace();
            return "ERROR:반납 처리 중 오류가 발생했습니다.";
        }
    }
}
