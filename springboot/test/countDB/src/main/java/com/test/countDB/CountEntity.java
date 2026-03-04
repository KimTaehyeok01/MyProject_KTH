package com.test.countDB;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "count")
@Entity
@Getter
@NoArgsConstructor
public class CountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer countNo;
    @Column(name = "count", nullable = false)
    private Integer count;

    @Builder
    public CountEntity(Integer countNo, Integer count) {
        this.countNo = countNo;
        this.count = count;
    }

    public CountEntity(CountEntity entity) {
    }

    public void update(Integer count) {
        this.count = count;
    }
}
