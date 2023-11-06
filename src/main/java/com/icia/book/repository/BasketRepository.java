package com.icia.book.repository;

import com.icia.book.entity.BasketEntity;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BasketRepository extends JpaRepository<BasketEntity, Long> {
    Optional<BasketEntity> findByMemberEntityAndBookEntity(MemberEntity memberEntity, BookEntity bookEntity);
}
