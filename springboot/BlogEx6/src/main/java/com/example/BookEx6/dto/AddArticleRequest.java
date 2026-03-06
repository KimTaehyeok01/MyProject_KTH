package com.example.BookEx6.dto;

import com.example.BookEx6.domain.Article;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class AddArticleRequest {
    private Long id;
    private String userName;
    private String title;
    private String content;

    @Builder
    public AddArticleRequest(Long id, String title, String userName, String content) {
        this.id = id;
        this.title = title;
        this.userName = userName;
        this.content = content;
    }

    // Dto -> Entity
    public Article toEntity() {
        return Article.builder()
                .id(id)
                .title(title)
                .userName(userName)
                .content(content)
                .build();
    }
}
