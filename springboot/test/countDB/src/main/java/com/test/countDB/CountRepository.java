package com.test.countDB;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountRepository extends JpaRepository<CountEntity, Integer> {
}
