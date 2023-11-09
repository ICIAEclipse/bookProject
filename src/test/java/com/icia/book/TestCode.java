package com.icia.book;

import com.icia.book.dto.OrderDTO;
import com.icia.book.repository.OrderRepository;
import com.icia.book.service.OrderService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

@SpringBootTest
public class TestCode {
    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;

    @Test
    @DisplayName("오더에서오더디테일DTO로 가져오나")
    public void orderOrderDetail(){
        Page<OrderDTO> orderDTOList = orderService.findAllByMemberEntity(1L, 1);
        for(OrderDTO  orderDTO : orderDTOList){
            System.out.println(orderDTO.getOrderDetailDTOList());
        }
    }

}
