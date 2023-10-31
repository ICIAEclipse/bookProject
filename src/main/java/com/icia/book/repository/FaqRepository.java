package com.icia.book.repository;

import com.icia.book.entity.CsCenterCategoryEntity;
import com.icia.book.entity.FaqEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FaqRepository extends JpaRepository<FaqEntity, Long> {
    Page<FaqEntity> findByCscenterCategoryEntity(CsCenterCategoryEntity csCenterCategoryEntity, PageRequest pageRequest);

    Page<FaqEntity> findByFaqTitleContaining(String q, PageRequest pageRequest);

    Page<FaqEntity> findByCscenterCategoryEntityAndFaqTitleContaining(CsCenterCategoryEntity csCenterCategoryEntity, String q, PageRequest pageRequest);

}
