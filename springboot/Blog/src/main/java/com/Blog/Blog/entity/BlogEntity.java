package com.Blog.Blog.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@Table(name = "blog")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlogEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_title", nullable = false)
    private String userTitle;

    @Column(name = "user_content", nullable = false)
    private String userContent;

    @Column(name = "date_of_write", nullable = false)
    private LocalDate dateOfWrite;

    public void update(String userTitle, String userContent) {
        this.userTitle = userTitle;
        this.userContent = userContent;
    }
}