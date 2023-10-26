package com.icia.book.repository;

import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Page<AddressEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable);
}
