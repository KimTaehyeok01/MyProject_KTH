package com.study.Ex13VMDB;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ★ @Controller → @RestController 로 변경
// @RestController = @Controller + @ResponseBody
// 뷰(HTML)를 반환하지 않고, JSON 데이터를 직접 반환합니다.
@RestController
@RequestMapping("/api/products") // 모든 URL 앞에 /api/products 가 붙습니다
public class ProductController {

    @Autowired
    private ProductService service;

    // ─────────────────────────────────────────
    // 1. 전체 조회   GET /api/products
    // ─────────────────────────────────────────
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> findAll() {
        List<ProductResponseDto> list = service.findAll();
        return ResponseEntity.ok(list); // HTTP 200 + JSON 리스트 반환
    }

    // ─────────────────────────────────────────
    // 2. 단건 조회   GET /api/products/{id}
    // ─────────────────────────────────────────
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findById(@PathVariable Integer id) {
        ProductResponseDto dto = service.findById(id);
        return ResponseEntity.ok(dto); // HTTP 200 + JSON 1건 반환
    }

    // ─────────────────────────────────────────
    // 3. 추가하기    POST /api/products
    //    Body : { "productName":"사과", "productPrice":1000, "productLimitDate":"2025-12-31" }
    // ─────────────────────────────────────────
    @PostMapping
    public ResponseEntity<String> save(@RequestBody ProductRequestDto dto) {
        // ★ @ModelAttribute → @RequestBody 로 변경
        // 폼(form) 데이터 대신 JSON 바디를 받습니다.
        service.save(dto);
        return ResponseEntity.ok("상품이 추가되었습니다.");
    }

    // ─────────────────────────────────────────
    // 4. 수정하기    PUT /api/products/{id}
    //    Body : { "productName":"바나나", "productPrice":2000, "productLimitDate":"2025-11-30" }
    // ─────────────────────────────────────────
    @PutMapping("/{id}")
    public ResponseEntity<String> update(@PathVariable Integer id,
                                         @RequestBody ProductRequestDto dto) {
        // ★ @PostMapping("/editProduct") → @PutMapping("/{id}") 로 변경
        // 수정은 PUT 메서드를 사용합니다.
        service.update(id, dto);
        return ResponseEntity.ok("상품이 수정되었습니다.");
    }

    // ─────────────────────────────────────────
    // 5. 삭제하기    DELETE /api/products/{id}
    // ─────────────────────────────────────────
    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {
        // ★ @GetMapping("/deleteProduct") → @DeleteMapping("/{id}") 로 변경
        // 삭제는 DELETE 메서드를 사용합니다.
        service.delete(id);
        return ResponseEntity.ok("상품이 삭제되었습니다.");
    }
}
