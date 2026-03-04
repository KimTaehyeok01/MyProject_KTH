package com.study.Ex12CalcDB;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Table(name = "history")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class CalcEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "history_no", nullable = false)
    private Integer historyNo;

    @Column(name="op", nullable = false)
    private String op;

    @Column(name = "input1", nullable = false)
    private Integer input1;

    @Column(name = "input2", nullable = false)
    private Integer input2;

    @Column(name ="result", nullable = false)
    private Integer result;
}
























