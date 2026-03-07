package com.Blog.Blog.dto;

import com.Blog.Blog.entity.BlogEntity;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class BlogResponseDto {
    private Long id;
    private String userTitle;
    private String userContent;
    private LocalDate dateOfWrite;

    public BlogResponseDto(BlogEntity entity){
        this.id = entity.getId();
        this.userTitle = entity.getUserTitle();
        this.userContent = entity.getUserContent();
        this.dateOfWrite = entity.getDateOfWrite();
    }
}
