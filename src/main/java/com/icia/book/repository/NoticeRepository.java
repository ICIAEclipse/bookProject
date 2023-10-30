package com.icia.book.repository;

import com.icia.book.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    Page<NoticeEntity> findByNoticeContentsContaining(String q, PageRequest pageRequest);
}
