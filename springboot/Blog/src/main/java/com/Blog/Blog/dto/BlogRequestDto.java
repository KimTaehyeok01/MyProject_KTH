package com.Blog.Blog.dto;

import com.Blog.Blog.entity.BlogEntity;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class BlogRequestDto {
    private String userTitle;
    private String userContent;
    private LocalDate dateOfWrite;

    @Builder
    public BlogRequestDto(String userTitle, String userContent, LocalDate dateOfWrite){
        this.userTitle = userTitle;
        this.userContent = userContent;
    }

    public BlogEntity toSaveEntity(){
        return BlogEntity.builder()
                .userTitle(userTitle)
                .userContent(userContent)
                .dateOfWrite(LocalDate.now())
                .build();
    }
}
