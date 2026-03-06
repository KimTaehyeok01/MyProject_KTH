package com.example.BookEx6.repository;

import com.example.BookEx6.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface BlogRepository extends JpaRepository<Article, Long> {
    //    @Query(value = "SELECT * FROM Article where title = ?1", nativeQuery = true)
    // List<Article> findByTitle(String title); // 전부 적어야 나옴
    List<Article> findByTitleContaining(String title); // SQL like문
}

