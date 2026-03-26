package com.study.LibraryStie.entity.book;

import jakarta.persistence.*;
import lombok.*;

// 도서 엔티티
@Entity
@Table(name = "book")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;           // 도서명

    @Column(name = "author", nullable = false)
    private String author;          // 저자

    @Column(name = "publisher")
    private String publisher;       // 출판사

    @Column(name = "isbn", unique = true)
    private String isbn;            // ISBN

    @Column(name = "category")
    private String category;        // 카테고리

    @Column(name = "totalQuantity", nullable = false)
    private int totalQuantity;      // 전체 수량

    @Column(name = "availableQuantity", nullable = false)
    private int availableQuantity;  // 대출 가능 수량

    @Column(name = "publishDate")
    private String publishDate;     // 출판일

    @Column(name = "description", length = 1000)
    private String description;     // 도서 설명

    // 도서 정보 수정
    public void update(String title, String author, String publisher,
                       String isbn, String category, int totalQuantity,
                       String publishDate, String description) {
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.isbn = isbn;
        this.category = category;
        this.totalQuantity = totalQuantity;
        this.publishDate = publishDate;
        this.description = description;
    }

    // 대출 시 가용 수량 감소
    public void decreaseAvailable() {
        if (this.availableQuantity > 0) {
            this.availableQuantity--;
        }
    }

    // 반납 시 가용 수량 증가
    public void increaseAvailable() {
        if (this.availableQuantity < this.totalQuantity) {
            this.availableQuantity++;
        }
    }
}
