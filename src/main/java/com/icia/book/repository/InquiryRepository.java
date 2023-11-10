package com.icia.book.repository;

import com.icia.book.entity.InquiryEntity;
import com.icia.book.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {

    Page<InquiryEntity> findByMemberEntity(MemberEntity memberEntity, PageRequest pageRequest);

    Page<InquiryEntity> findByInquiryStatus(int status, PageRequest pageRequest);
}
