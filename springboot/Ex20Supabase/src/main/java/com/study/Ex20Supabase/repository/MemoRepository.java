package com.study.Ex20Supabase.repository;

import com.study.Ex20Supabase.entity.Memo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoRepository extends JpaRepository<Memo, Long> {
}
