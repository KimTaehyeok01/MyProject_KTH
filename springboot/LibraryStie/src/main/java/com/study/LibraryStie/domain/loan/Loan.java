package com.study.LibraryStie.domain.loan;

import com.study.LibraryStie.domain.book.Book;
import com.study.LibraryStie.enumeration.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "loan")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "userEmail", nullable = false)
    private String userEmail;

    @Column(name = "userName", nullable = false)
    private String userName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;

    @Column(name = "loanDate", nullable = false)
    private LocalDate loanDate;

    @Column(name = "expectedReturnDate", nullable = false)
    private LocalDate expectedReturnDate;

    @Column(name = "returnDate")
    private LocalDate returnDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "loanStatus", nullable = false)
    private LoanStatus loanStatus;

    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.loanStatus = LoanStatus.RETURNED;
    }
}
