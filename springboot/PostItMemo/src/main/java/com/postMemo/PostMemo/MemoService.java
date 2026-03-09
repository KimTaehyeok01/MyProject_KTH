package com.postMemo.PostMemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MemoService {
    @Autowired
    private MemoRepository repository;

    // 전체 조회
    @Transactional(readOnly = true)
    public List<MemoResponseDto> findAll(){
        List<MemoEntity> list = repository.findAll();
        return list.stream().map(MemoResponseDto :: from).toList();
    }

    // 생성
    @Transactional
    public void create(MemoRequestDto dto) {
        repository.save(dto.toEntity());
    }

    // 수정 (제목·내용·위치) 
    @Transactional
    public void update(Long id, MemoRequestDto dto) {
        MemoEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));
        entity.update(dto.getTitle(), dto.getContent(), dto.getPosX(), dto.getPosY(), dto.getZIndex());
    }

    // 색상 변경
    @Transactional
    public void updateColor(Long id, String color) {
        MemoEntity entity = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("메모를 찾을 수 없습니다."));
        entity.updateColor(MemoEntity.MemoColor.valueOf(color));
        repository.save(entity);
    }

    // 삭제
    @Transactional
    public void delete(Long id) {
        MemoEntity entity = repository.findById(id).orElseThrow(()-> 
                        new IllegalArgumentException("삭제 실패"));
        repository.delete(entity);
    }

    // 전체 삭제
    @Transactional
    public void deleteAll() {
        repository.deleteAll();
    }
}
