package com.study.LibraryStie.service;

import com.study.LibraryStie.dto.BookRequest;
import com.study.LibraryStie.dto.BookResponse;
import com.study.LibraryStie.entity.book.Book;
import com.study.LibraryStie.entity.book.BookRepository;
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

    // 도서 목록 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<BookResponse> getList(int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return bookRepository.findAll(pageable).map(BookResponse::new);
    }

    // 도서 검색 (제목 또는 저자, 페이징)
    @Transactional(readOnly = true)
    public Page<BookResponse> search(String keyword, int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        if (!StringUtils.hasText(keyword)) {
            return bookRepository.findAll(pageable).map(BookResponse::new);
        }
        return bookRepository.findByTitleContainingOrAuthorContaining(keyword, keyword, pageable)
                .map(BookResponse::new);
    }

    // 카테고리별 조회 (페이징)
    @Transactional(readOnly = true)
    public Page<BookResponse> findByCategory(String category, int page) {
        Pageable pageable = PageRequest.of(page, 12, Sort.by("id").descending());
        return bookRepository.findByCategory(category, pageable).map(BookResponse::new);
    }

    // 도서 단건 조회
    @Transactional(readOnly = true)
    public BookResponse findById(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("도서를 찾을 수 없습니다."));
        return new BookResponse(book);
    }

    // 전체 도서 조회 (관리자)
    @Transactional(readOnly = true)
    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(BookResponse::new).collect(Collectors.toList());
    }

    // 도서 등록 (관리자)
    @Transactional
    public void save(BookRequest dto) {
        bookRepository.save(dto.toSaveEntity());
    }

    // 도서 수정 (관리자)
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

    // 도서 삭제 (관리자)
    @Transactional
    public void delete(Long id) {
        Book book = bookRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("도서를 찾을 수 없습니다."));
        bookRepository.delete(book);
    }

    // 전체 도서 수
    @Transactional(readOnly = true)
    public long count() {
        return bookRepository.count();
    }
}
