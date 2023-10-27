package com.icia.book.repository;

import com.icia.book.entity.CsCenterCategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CsCenterCategoryRepository extends JpaRepository<CsCenterCategoryEntity, Long> {

    Optional<CsCenterCategoryEntity> findByCenterCategoryId(String centerCategoryId);

    Optional<CsCenterCategoryEntity> findByCenterName(String centerName);
}
