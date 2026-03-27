package com.study.LibraryStie.dto;

import com.study.LibraryStie.domain.book.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookRequest {

    private Long id;

    @NotBlank(message = "도서명을 입력하세요")
    private String title;

    @NotBlank(message = "저자를 입력하세요")
    private String author;

    private String publisher;
    private String isbn;
    private String category;

    @Min(value = 1, message = "수량은 1권 이상이어야 합니다")
    private int totalQuantity;

    private String publishDate;
    private String description;

    public Book toSaveEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .category(category)
                .totalQuantity(totalQuantity)
                .availableQuantity(totalQuantity)
                .publishDate(publishDate)
                .description(description)
                .build();
    }
}
