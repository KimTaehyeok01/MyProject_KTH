package com.study.LibraryStie.domain.book;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long> {

    Page<Book> findByTitleContainingOrAuthorContaining(String title, String author, Pageable pageable);

    Page<Book> findByCategory(String category, Pageable pageable);

    List<Book> findByCategory(String category);

    List<Book> findByTitleContaining(String title);
}
