package com.icia.book.repository;

import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {

    List<CommentEntity> findByBookEntityOrderByIdDesc(BookEntity bookEntity);
}
