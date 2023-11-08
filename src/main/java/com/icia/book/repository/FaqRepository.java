package com.icia.book.repository;

import com.icia.book.entity.CsCenterCategoryEntity;
import com.icia.book.entity.FaqEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface FaqRepository extends JpaRepository<FaqEntity, Long> {

    @Modifying
    @Query(value = "update FaqEntity f set f.faqHits = f.faqHits+1 where f.id=:id")
    void upHits(@Param("id") Long id);
    Page<FaqEntity> findByCscenterCategoryEntity(CsCenterCategoryEntity csCenterCategoryEntity, PageRequest pageRequest);

    Page<FaqEntity> findByFaqTitleContaining(String q, PageRequest pageRequest);

    Page<FaqEntity> findByCscenterCategoryEntityAndFaqTitleContaining(CsCenterCategoryEntity csCenterCategoryEntity, String q, PageRequest pageRequest);

}
