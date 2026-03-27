package com.study.LibraryStie.controller;

import com.study.LibraryStie.dto.BookResponse;
import com.study.LibraryStie.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Page<BookResponse> getBooks(@RequestParam(defaultValue = "") String keyword,
                                       @RequestParam(defaultValue = "") String category,
                                       @RequestParam(defaultValue = "0") int page) {
        if (!category.isEmpty()) {
            return bookService.findByCategory(category, page);
        }
        return bookService.search(keyword, page);
    }

    @GetMapping("/{id}")
    public BookResponse getBook(@PathVariable Long id) {
        return bookService.findById(id);
    }
}
