package com.icia.book.repository;

import com.icia.book.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Page<CategoryEntity> findByCategoryIdContaining(String q, PageRequest id);

    Page<CategoryEntity> findByCategoryNameContaining(String q, PageRequest id);

}
