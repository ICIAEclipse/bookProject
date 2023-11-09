package com.icia.book.dto;

import com.icia.book.contoroller.util.UtilClass;
import com.icia.book.entity.OrderDetailEntity;
import com.icia.book.entity.OrderEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDTO {
    private Long id;
    private String orderCode;
    private String orderMemberName;
    private String orderMemberMobile;
    private int orderTotal;
    private String orderAddress;
    private String orderAddressDetail;
    private String orderPostCode;
    private int orderStatus;
    private String orderDate;
    private Long memberId;
    private List<OrderDetailDTO> orderDetailDTOList;

    public static OrderDTO toDTO(OrderEntity orderEntity) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(orderEntity.getId());
        orderDTO.setOrderCode(orderEntity.getOrderCode());
        orderDTO.setOrderMemberName(orderEntity.getOrderMemberName());
        orderDTO.setOrderMemberMobile(orderEntity.getOrderMemberMobile());
        orderDTO.setOrderTotal(orderEntity.getOrderTotal());
        orderDTO.setOrderAddress(orderEntity.getOrderAddress());
        orderDTO.setOrderAddressDetail(orderEntity.getOrderAddressDetail());
        orderDTO.setOrderPostCode(orderEntity.getOrderPostCode());
        orderDTO.setOrderStatus(orderEntity.getOrderStatus());
        orderDTO.setOrderDate(UtilClass.dateTimeFormat(orderEntity.getOrderDate()));
        orderDTO.setMemberId(orderEntity.getMemberEntity().getId());
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for(OrderDetailEntity orderDetailEntity : orderEntity.getOrderDetailEntityList()){
            orderDetailDTOList.add(OrderDetailDTO.toDTO(orderDetailEntity));
        }
        orderDTO.setOrderDetailDTOList(orderDetailDTOList);
        return orderDTO;
    }
}
