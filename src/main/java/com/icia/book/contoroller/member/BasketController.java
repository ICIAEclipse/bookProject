package com.icia.book.contoroller.member;

import com.icia.book.dto.BookDTO;
import com.icia.book.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@RequestMapping("/basket")
@Controller
public class BasketController {

    @PostMapping
    public ResponseEntity basket (@RequestBody String isbn, HttpSession session) {
        System.out.println("찜 테스트중 " + isbn);
        String loginEmail = (String) session.getAttribute("loginEmail");
        BasketService.save(isbn, loginEmail);
        return new ResponseEntity("basket", HttpStatus.OK);

    }
}