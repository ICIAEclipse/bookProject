package com.icia.book;

import com.icia.book.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestCode {
    @Autowired
    private OrderService orderService;

}
