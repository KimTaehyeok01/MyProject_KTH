package com.study.Ex12CalcDB;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CalcRepository extends JpaRepository<CalcEntity, Integer> {
}
