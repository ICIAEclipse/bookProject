package com.icia.book.entity;

import com.icia.book.dto.OrderDetailDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "order_detail_table")
@Entity
public class OrderDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 1000)
    private String bookProfile;

    @Column(length = 30, nullable = false)
    private String bookName;

    @Column(nullable = false)
    private int bookCount;

    @Column(nullable = false)
    private int bookPrice;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private OrderEntity orderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static OrderDetailEntity toSaveEntity(OrderDetailDTO orderDetailDTO, MemberEntity memberEntity, BookEntity bookEntity, OrderEntity orderEntity){
        OrderDetailEntity orderDetailEntity = new OrderDetailEntity();
        orderDetailEntity.setBookName(orderDetailDTO.getBookName());
        orderDetailEntity.setBookPrice(orderDetailDTO.getBookPrice());
        orderDetailEntity.setBookCount(orderDetailDTO.getBookCount());
        orderDetailEntity.setMemberEntity(memberEntity);
        orderDetailEntity.setBookEntity(bookEntity);
        orderDetailEntity.setOrderEntity(orderEntity);
        return orderDetailEntity;
    }
}
