package com.icia.book.repository;

import com.icia.book.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findByCategoryIdContaining(String q, PageRequest pageRequest);

    Page<CategoryEntity> findByCategoryNameContaining(String q, PageRequest pageRequest);

    Optional<CategoryEntity> findByCategoryId(String id);
}
