package com.icia.book.repository;

import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<AddressEntity, Long> {
    Page<AddressEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable);

    Optional<AddressEntity> findByMemberEntityAndAddressStatus(MemberEntity memberEntity, int AddressStatus);

    List<AddressEntity> findAllByMemberEntity(MemberEntity memberEntity);
}
