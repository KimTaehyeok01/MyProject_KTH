package com.study.LibraryStie.dto;

import com.study.LibraryStie.domain.loan.Loan;
import com.study.LibraryStie.enumeration.LoanStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

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
    private boolean overdue;

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
        this.overdue = (loan.getLoanStatus() == LoanStatus.BORROWED)
                && LocalDate.now().isAfter(loan.getExpectedReturnDate());
    }
}
