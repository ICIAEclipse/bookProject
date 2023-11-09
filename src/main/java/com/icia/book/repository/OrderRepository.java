package com.icia.book.repository;

import com.icia.book.entity.MemberEntity;
import com.icia.book.entity.OrderEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, Long> {
    Optional<OrderEntity> findByOrderCode(String orderCode);


    Page<OrderEntity> findByOrderStatus(int page, PageRequest pageRequest);

    Page<OrderEntity> findAllByMemberEntity(MemberEntity memberEntity, Pageable pageable);

}
