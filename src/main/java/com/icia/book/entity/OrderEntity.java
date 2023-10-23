package com.icia.book.entity;


import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "order_table")
@Entity
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String orderMemberName;

    @Column(nullable = false)
    private int orderTotal;

    @Column(length = 50, nullable = false)
    private String orderAddress;

    @Column(length = 50, nullable = false)
    private String orderAddressDetail;

    @Column(length = 50, nullable = false)
    private String orderMemberMobile;

    @Column(length = 50, nullable = false)
    private String orderPostCode;

    @Column(nullable = false)
    private int orderStatus;

    @CreationTimestamp
    @Column()
    private LocalDateTime orderDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetailEntityList;
}
