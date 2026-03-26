package com.study.LibraryStie.dto;

import com.study.LibraryStie.entity.book.Book;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// 도서 정보 응답 DTO
@Getter @Setter
@NoArgsConstructor
public class BookResponse {

    private Long id;
    private String title;
    private String author;
    private String publisher;
    private String isbn;
    private String category;
    private int totalQuantity;
    private int availableQuantity;
    private String publishDate;
    private String description;

    // Book 엔티티를 DTO로 변환하는 생성자
    public BookResponse(Book book) {
        this.id = book.getId();
        this.title = book.getTitle();
        this.author = book.getAuthor();
        this.publisher = book.getPublisher();
        this.isbn = book.getIsbn();
        this.category = book.getCategory();
        this.totalQuantity = book.getTotalQuantity();
        this.availableQuantity = book.getAvailableQuantity();
        this.publishDate = book.getPublishDate();
        this.description = book.getDescription();
    }
}
