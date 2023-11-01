package com.icia.book.entity;


import com.icia.book.dto.OrderDTO;
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

    @Column(length = 19, nullable = false, unique = true)
    private String orderCode;

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

    @OneToMany(mappedBy = "orderEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetailEntityList;

    public static OrderEntity toSaveEntity(OrderDTO orderDTO, MemberEntity memberEntity) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderCode(orderDTO.getOrderCode());
        orderEntity.setOrderTotal(orderDTO.getOrderTotal());
        orderEntity.setOrderPostCode(orderDTO.getOrderPostCode());
        orderEntity.setOrderAddress(orderDTO.getOrderAddress());
        orderEntity.setOrderAddressDetail(orderDTO.getOrderAddressDetail());
        orderEntity.setOrderMemberName(orderDTO.getOrderMemberName());
        orderEntity.setOrderMemberMobile(orderDTO.getOrderMemberMobile());
        orderEntity.setMemberEntity(memberEntity);
        return orderEntity;
    }
}
