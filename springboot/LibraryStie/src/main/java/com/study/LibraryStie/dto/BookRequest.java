package com.study.LibraryStie.dto;

import com.study.LibraryStie.domain.book.Book;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

// 도서 등록/수정 요청 DTO
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

    // 도서 등록 시 엔티티로 변환
    public Book toSaveEntity() {
        return Book.builder()
                .title(title)
                .author(author)
                .publisher(publisher)
                .isbn(isbn)
                .category(category)
                .totalQuantity(totalQuantity)
                .availableQuantity(totalQuantity) // 처음엔 전체 수량이 가용 수량
                .publishDate(publishDate)
                .description(description)
                .build();
    }
}
