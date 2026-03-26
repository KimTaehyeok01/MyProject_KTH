package com.study.LibraryStie.dto;

import com.study.LibraryStie.entity.loan.Loan;
import com.study.LibraryStie.enumeration.LoanStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// 대출 정보 응답 DTO
@Getter @Setter
@NoArgsConstructor
public class LoanResponse {

    private Long id;
    private String userEmail;
    private String userName;
    private Long bookId;
    private String bookTitle;
    private String bookAuthor;
    private LocalDate loanDate;
    private LocalDate expectedReturnDate;
    private LocalDate returnDate;
    private LoanStatus loanStatus;
    private String loanStatusValue;
    private boolean overdue; // 연체 여부

    // Loan 엔티티를 DTO로 변환하는 생성자
    public LoanResponse(Loan loan) {
        this.id = loan.getId();
        this.userEmail = loan.getUserEmail();
        this.userName = loan.getUserName();
        this.bookId = loan.getBook().getId();
        this.bookTitle = loan.getBook().getTitle();
        this.bookAuthor = loan.getBook().getAuthor();
        this.loanDate = loan.getLoanDate();
        this.expectedReturnDate = loan.getExpectedReturnDate();
        this.returnDate = loan.getReturnDate();
        this.loanStatus = loan.getLoanStatus();
        this.loanStatusValue = loan.getLoanStatus().getValue();
        // 반납 예정일이 오늘보다 지났고 아직 반납 안 됐으면 연체
        this.overdue = (loan.getLoanStatus() == LoanStatus.BORROWED)
                && LocalDate.now().isAfter(loan.getExpectedReturnDate());
    }
}
