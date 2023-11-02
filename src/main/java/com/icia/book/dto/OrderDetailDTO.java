package com.icia.book.dto;

import com.icia.book.entity.OrderDetailEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private Long id;
    private String bookProfile;
    private String bookName;
    private int bookCount;
    private int bookPrice;
    private Long bookId;
    private Long orderId;
    private Long memberId;

    public static OrderDetailDTO toDTO(OrderDetailEntity orderDetailEntity) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setId(orderDetailEntity.getId());
        orderDetailDTO.setBookProfile(orderDetailEntity.getBookProfile());
        orderDetailDTO.setBookName(orderDetailEntity.getBookName());
        orderDetailDTO.setBookCount(orderDetailEntity.getBookCount());
        orderDetailDTO.setBookPrice(orderDetailEntity.getBookPrice());
        orderDetailDTO.setBookId(orderDetailEntity.getBookEntity().getId());
        orderDetailDTO.setMemberId(orderDetailEntity.getMemberEntity().getId());
        orderDetailDTO.setOrderId(orderDetailEntity.getOrderEntity().getId());
        return orderDetailDTO;
    }
}
