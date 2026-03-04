package com.study.Ex13VMDB;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
public class ProductResponseDto {
    private Integer productNo;
    private String productName;
    private Integer productPrice;
    private LocalDate productLimitDate;

    // Dto -> Entity
    public ProductResponseDto(ProductEntity entity) {
        this.productNo = entity.getProductNo();
        this.productName = entity.getProductName();
        this.productPrice = entity.getProductPrice();
        this.productLimitDate = entity.getProductLimitDate();
    }
}
