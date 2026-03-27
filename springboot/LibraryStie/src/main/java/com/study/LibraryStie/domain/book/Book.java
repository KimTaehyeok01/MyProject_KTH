package com.study.LibraryStie.domain.book;

import jakarta.persistence.*;
import lombok.*;

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
    private String title;

    @Column(name = "author", nullable = false)
    private String author;

    @Column(name = "publisher")
    private String publisher;

    @Column(name = "isbn", unique = true)
    private String isbn;

    @Column(name = "category")
    private String category;

    @Column(name = "totalQuantity", nullable = false)
    private int totalQuantity;

    @Column(name = "availableQuantity", nullable = false)
    private int availableQuantity;

    @Column(name = "publishDate")
    private String publishDate;

    @Column(name = "description", length = 1000)
    private String description;

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

    public void decreaseAvailable() {
        if (this.availableQuantity > 0) {
            this.availableQuantity--;
        }
    }

    public void increaseAvailable() {
        if (this.availableQuantity < this.totalQuantity) {
            this.availableQuantity++;
        }
    }
}
