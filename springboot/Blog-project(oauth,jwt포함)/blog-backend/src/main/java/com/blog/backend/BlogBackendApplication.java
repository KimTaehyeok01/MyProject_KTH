package com.blog.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // createdAt, updatedAt 자동 관리
public class BlogBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(BlogBackendApplication.class, args);
    }
}
