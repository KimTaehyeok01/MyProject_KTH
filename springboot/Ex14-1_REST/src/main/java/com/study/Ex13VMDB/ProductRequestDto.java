package com.study.Ex13VMDB;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

// ★ @DateTimeFormat 제거 → JSON은 "yyyy-MM-dd" 문자열을 LocalDate로 자동 변환해줍니다.
@Getter @Setter
@NoArgsConstructor
public class ProductRequestDto {
    private Integer productNo;
    private String productName;
    private Integer productPrice;
    private LocalDate productLimitDate;  // JSON에서 "2025-12-31" 형태로 받습니다

    public ProductRequestDto(String productName, Integer productPrice, LocalDate productLimitDate) {
        this.productName = productName;
        this.productPrice = productPrice;
        this.productLimitDate = productLimitDate;
    }

    // Dto → Entity 변환
    public ProductEntity toSaveEntity() {
        return ProductEntity.builder()
                .productName(productName)
                .productPrice(productPrice)
                .productLimitDate(productLimitDate) // ★ LocalDate.now() → dto에서 받은 날짜로 수정
                .build();
    }
}
