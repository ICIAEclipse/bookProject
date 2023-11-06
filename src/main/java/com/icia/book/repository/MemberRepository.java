package com.icia.book.repository;

import com.icia.book.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

    Page<MemberEntity> findByMemberEmailContaining(String q, PageRequest pageRequest);

    Page<MemberEntity> findByMemberNameContaining(String q, PageRequest pageRequest);
}
