package com.example.BookEx6.dto;

import com.example.BookEx6.domain.Article;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class AddArticleResponse {
    private Long id;
    private String title;
    private String userName;
    private String content;

    // Entity -> Dto
    public AddArticleResponse(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.userName = article.getUserName();
        this.content = article.getContent();
    }
}
