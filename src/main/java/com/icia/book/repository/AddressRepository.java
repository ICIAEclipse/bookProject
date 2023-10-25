package com.icia.book.repository;

import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    List<AddressEntity> findAllByMemberEntity(MemberEntity memberEntity, Sort sort);
}
