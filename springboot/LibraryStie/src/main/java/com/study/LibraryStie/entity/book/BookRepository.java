package com.study.LibraryStie.entity.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    // 제목 또는 저자로 검색 (페이징)
    Page<Book> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);

    // 카테고리로 검색 (페이징)
    Page<Book> findByCategory(String category, Pageable pageable);

    // 카테고리 목록으로 검색
    List<Book> findByCategory(String category);

    // 제목으로 검색
    List<Book> findByTitleContaining(String title);
}
