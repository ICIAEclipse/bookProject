package com.icia.book.repository;

import com.icia.book.entity.InquiryCommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryCommentRepository extends JpaRepository<InquiryCommentEntity, Long> {
}
