package com.study.Ex13VMDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {
    @Autowired
    private ProductRepository repository;

    // 전체 조회
    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAll(){
        List<ProductEntity> list = repository.findAll();
        return list.stream().map(ProductResponseDto :: new).collect(Collectors.toList());
    }

    // 단건 조회
    @Transactional(readOnly = true)
    public ProductResponseDto findById(Integer id){
        ProductEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("조회 실패"));
        return new ProductResponseDto(entity);
    }

    // 저장하기
    @Transactional
    public void save(ProductRequestDto dto){
      repository.save(dto.toSaveEntity());
    }

    // 수정하기
    @Transactional
    public void update(Integer id, ProductRequestDto dto){
        ProductEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("수정 실패"));

        entity.update(dto.getProductName(), dto.getProductPrice(), dto.getProductLimitDate());
    }

    // 삭제하기
    @Transactional
    public void delete(Integer id){
        ProductEntity entity = repository.findById(id).orElseThrow(()->
                new IllegalArgumentException("삭제 실패"));
        repository.delete(entity);
    }
}












