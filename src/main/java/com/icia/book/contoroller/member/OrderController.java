package com.icia.book.contoroller.member;

import com.icia.book.service.BookService;
import com.icia.book.service.MemberService;
import com.icia.book.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final BookService bookService;


}
