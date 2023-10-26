package com.icia.book.repository;

import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {


    Page<BookEntity> findByBookAuthorContainingOrBookNameContaining(String q1, String q2, PageRequest pageRequest);

    Page<BookEntity> findByCategoryEntity(CategoryEntity categoryEntity, PageRequest pageRequest);

    Page<BookEntity> findByCategoryEntityAndBookAuthorContainingOrBookNameContaining(CategoryEntity categoryEntity, String q1, String q2, PageRequest pageRequest);
    Optional<BookEntity> findByIsbn(String isbn);
}
