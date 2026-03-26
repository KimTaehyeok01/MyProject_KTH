package com.study.LibraryStie.entity.loan;

import com.study.LibraryStie.enumeration.LoanStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LoanRepository extends JpaRepository<Loan, Long> {

    // 특정 회원의 전체 대출 이력 조회
    List<Loan> findByUserEmail(String userEmail);

    // 특정 회원의 현재 대출 중인 목록 조회
    List<Loan> findByUserEmailAndLoanStatus(String userEmail, LoanStatus loanStatus);

    // 전체 대출 현황 (상태별)
    List<Loan> findByLoanStatus(LoanStatus loanStatus);

    // 전체 대출 현황 (페이징)
    Page<Loan> findAll(Pageable pageable);

    // 특정 책 대출 이력 조회
    @Query(value = "SELECT * FROM loan l WHERE l.bookId = :bookId", nativeQuery = true)
    List<Loan> findByBookId(@Param("bookId") Long bookId);

    // 이미 대출 중인지 확인
    boolean existsByUserEmailAndBook_IdAndLoanStatus(String userEmail, Long bookId, LoanStatus loanStatus);

    // 대출 상태별 건수
    long countByLoanStatus(LoanStatus loanStatus);
}
