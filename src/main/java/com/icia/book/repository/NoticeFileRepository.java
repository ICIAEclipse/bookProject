package com.icia.book.repository;

import com.icia.book.entity.NoticeFileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeFileRepository extends JpaRepository<NoticeFileEntity, Long> {


    void deleteByStoredFileName(String name);
}
