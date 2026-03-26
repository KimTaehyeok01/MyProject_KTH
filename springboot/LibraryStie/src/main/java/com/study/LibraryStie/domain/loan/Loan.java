package com.study.LibraryStie.domain.loan;

import com.study.LibraryStie.domain.book.Book;
import com.study.LibraryStie.enumeration.LoanStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

// 대출 엔티티
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
    private String userEmail;           // 대출자 이메일

    @Column(name = "userName", nullable = false)
    private String userName;            // 대출자 이름

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "bookId", nullable = false)
    private Book book;                  // 대출한 책

    @Column(name = "loanDate", nullable = false)
    private LocalDate loanDate;         // 대출일

    @Column(name = "expectedReturnDate", nullable = false)
    private LocalDate expectedReturnDate; // 반납 예정일 (대출일 + 14일)

    @Column(name = "returnDate")
    private LocalDate returnDate;       // 실제 반납일 (null이면 대출 중)

    @Enumerated(EnumType.STRING)
    @Column(name = "loanStatus", nullable = false)
    private LoanStatus loanStatus;      // 대출 상태 (BORROWED, RETURNED)

    // 반납 처리 메서드
    public void returnBook() {
        this.returnDate = LocalDate.now();
        this.loanStatus = LoanStatus.RETURNED;
    }
}
