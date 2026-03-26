package com.study.LibraryStie.domain.loan;

import com.study.LibraryStie.enumeration.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    List<Loan> findByUserEmail(String userEmail);

    List<Loan> findByUserEmailAndLoanStatus(String userEmail, LoanStatus loanStatus);

    List<Loan> findByLoanStatus(LoanStatus loanStatus);

    Page<Loan> findAll(Pageable pageable);

    @Query(value = "SELECT * FROM loan l WHERE l.bookId = :bookId", nativeQuery = true)
    List<Loan> findByBookId(@Param("bookId") Long bookId);

    boolean existsByUserEmailAndBook_IdAndLoanStatus(String userEmail, Long bookId, LoanStatus loanStatus);

    long countByLoanStatus(LoanStatus loanStatus);
}
