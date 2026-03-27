package com.study.LibraryStie.service;

import com.study.LibraryStie.dto.BookRequest;
import com.study.LibraryStie.dto.BookResponse;
import com.study.LibraryStie.domain.book.Book;
import com.study.LibraryStie.domain.book.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    @Transactional(readOnly = true)
    public Page<BookResponse> getList(int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return bookRepository.findAll(pageable).map(BookResponse::new);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> search(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        if (!StringUtils.hasText(keyword)) {
            return bookRepository.findAll(pageable).map(BookResponse::new);
        }
        return bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword, pageable)
                .map(BookResponse::new);
    }

    @Transactional(readOnly = true)
    public Page<BookResponse> findByCategory(String category, int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return bookRepository.findByCategory(category, pageable).map(BookResponse::new);
    }

    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("도서를 찾을 수 없습니다."));
        return new BookResponse(book);
    }

    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    @Transactional
    public void save(BookRequest dto) {
        bookRepository.save(dto.toSaveEntity());
    }

    @Transactional
    public void update(Long id, BookRequest dto) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("도서를 찾을 수 없습니다."));
        book.update(
                dto.getTitle(), dto.getAuthor(), dto.getPublisher(),
                dto.getIsbn(), dto.getCategory(), dto.getTotalQuantity(),
                dto.getPublishDate(), dto.getDescription()
        );
    }

    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("도서를 찾을 수 없습니다."));
        bookRepository.delete(book);
    }

    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }
}
