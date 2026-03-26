package com.study.LibraryStie.controller;

import com.study.LibraryStie.dto.BookResponse;
import com.study.LibraryStie.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

// 도서 조회 REST API 컨트롤러
@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    // 도서 목록 조회 (검색 + 페이징) GET /api/books?keyword=&page=0
    @GetMapping
    public Page<BookResponse> getBooks(@RequestParam(defaultValue = "") String keyword,
                                       @RequestParam(defaultValue = "") String category,
                                       @RequestParam(defaultValue = "0") int page) {
        if (!category.isEmpty()) {
            return bookService.findByCategory(category, page);
        }
        return bookService.search(keyword, page);
    }

    // 도서 단건 조회 GET /api/books/{id}
    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
