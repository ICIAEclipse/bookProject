package com.icia.book.repository;


import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CartEntity;
import com.icia.book.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface CartRepository extends JpaRepository<CartEntity, Long> {
    Optional<CartEntity> findByMemberEntityAndBookEntity(MemberEntity memberEntity, BookEntity bookEntity);
}