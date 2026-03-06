package com.example.BookEx6.service;

import com.example.BookEx6.dto.AddArticleRequest;
import com.example.BookEx6.domain.Article;
import com.example.BookEx6.dto.AddArticleResponse;
import com.example.BookEx6.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BlogService {
    @Autowired
    private BlogRepository repository;

    // 전체 조회
    @Transactional(readOnly = true)
    public List<Article> findAll() {
        List<Article> list = repository.findAll();
        return list.stream().limit(10).collect(Collectors.toList());
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public AddArticleResponse findById(final Long id) {
        Article article = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("조회 실패"));
        return new AddArticleResponse(article);
    }

    // 검색
    @Transactional(readOnly = true)
    public List<AddArticleResponse> findByTitle(final String title) {
        List<Article> article = repository.findByTitleContaining(title);
       return article.stream().map(AddArticleResponse :: new).collect(Collectors.toList());
    }

    // 저장
    @Transactional
    public void save(AddArticleRequest request) {
        repository.save(request.toEntity());
    }

    // 수정
    @Transactional
    public void update(final Long id, AddArticleRequest request) {
        Article article = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("수정 실패"));
        article.update(request.getTitle(), request.getContent());
    }

    // 삭제
    @Transactional
    public void delete(final Long id) {
        Article article = repository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("삭제 실패"));
        repository.delete(article);
    }

    // 10개씩 조회
    @Transactional(readOnly = true)
    public Page<Article> getList(int page) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("id").descending());
        return repository.findAll(pageable);
    }
}












