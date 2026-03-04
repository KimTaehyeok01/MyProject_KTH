package com.study.Ex13VMDB;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "product")
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@AllArgsConstructor
@Builder
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_no", nullable = false)
    private Integer productNo;

    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(name = "product_price", nullable = false)
    private Integer productPrice;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "product_limit_date", columnDefinition = "DATE", nullable = false)
    private LocalDate productLimitDate;


    public void update(String productName, Integer productPrice, LocalDate productLimitDate){
        this.productName = productName;
        this.productPrice = productPrice;
        this.productLimitDate = productLimitDate;
    }
}












