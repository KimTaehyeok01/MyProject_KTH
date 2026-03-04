package com.study.Ex13VMDB;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor
public class ProductRequestDto {
    private Integer productNo;
    private String productName;
    private Integer productPrice;
    private LocalDate productLimitDate;

    public ProductRequestDto(String productName, Integer productPrice, LocalDate productLimitDate) {
        this.productLimitDate = productLimitDate;
        this.productPrice = productPrice;
        this.productName = productName;
    }

    // Dto -> Entity
    public ProductEntity toSaveEntity() {
        return ProductEntity.builder()
                .productName(productName)
                .productPrice(productPrice)
                .productLimitDate(LocalDate.now())
                .build();
    }
}
