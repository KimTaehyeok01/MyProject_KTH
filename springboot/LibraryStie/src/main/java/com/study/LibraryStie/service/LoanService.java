package com.study.LibraryStie.service;

import com.study.LibraryStie.dto.LoanResponse;
import com.study.LibraryStie.domain.book.Book;
import com.study.LibraryStie.domain.book.BookRepository;
import com.study.LibraryStie.domain.loan.Loan;
import com.study.LibraryStie.domain.loan.LoanRepository;
import com.study.LibraryStie.enumeration.LoanStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LoanService {

    private final LoanRepository loanRepository;
    private final BookRepository bookRepository;

    @Transactional
    public void loanBook(Long bookId, String userEmail, String userName) {
        Book book = bookRepository.findById(bookId).orElseThrow(() ->
                new IllegalArgumentException("도서를 찾을 수 없습니다."));

        if (book.getAvailableQuantity() <= 0) {
            throw new IllegalArgumentException("대출 가능한 도서가 없습니다. 반납 후 다시 시도해 주세요.");
        }

        if (loanRepository.existsByUserEmailAndBook_IdAndLoanStatus(
                userEmail, bookId, LoanStatus.BORROWED)) {
            throw new IllegalArgumentException("이미 대출 중인 도서입니다.");
        }

        book.decreaseAvailable();

        Loan loan = Loan.builder()
                .userEmail(userEmail)
                .userName(userName)
                .book(book)
                .loanDate(LocalDate.now())
                .expectedReturnDate(LocalDate.now().plusDays(14))
                .loanStatus(LoanStatus.BORROWED)
                .build();

        loanRepository.save(loan);
    }

    @Transactional
    public void returnBook(Long loanId, String userEmail) {
        Loan loan = loanRepository.findById(loanId).orElseThrow(() ->
                new IllegalArgumentException("대출 정보를 찾을 수 없습니다."));

        if (!loan.getUserEmail().equals(userEmail)) {
            throw new IllegalArgumentException("본인이 대출한 도서만 반납할 수 있습니다.");
        }

        if (loan.getLoanStatus() == LoanStatus.RETURNED) {
            throw new IllegalArgumentException("이미 반납된 도서입니다.");
        }

        loan.returnBook();
        loan.getBook().increaseAvailable();
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> findMyActiveLoans(String userEmail) {
        return loanRepository.findByUserEmailAndLoanStatus(userEmail, LoanStatus.BORROWED)
                .stream().map(LoanResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> findMyAllLoans(String userEmail) {
        return loanRepository.findByUserEmail(userEmail)
                .stream().map(LoanResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<LoanResponse> findAll() {
        return loanRepository.findAll()
                .stream().map(LoanResponse::new).collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public long countActiveLoan() {
        return loanRepository.countByLoanStatus(LoanStatus.BORROWED);
    }
}
