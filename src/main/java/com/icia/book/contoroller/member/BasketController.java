package com.icia.book.contoroller.member;

import com.icia.book.dto.BasketDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("/basket")
@Controller
public class BasketController {
    private final BasketService basketService;

    @PostMapping
    public ResponseEntity basket (@RequestBody BasketDTO basketDTO) {

        try {
            basketService.save(basketDTO.getIsbn(), basketDTO.getMemberEmail());
            return new ResponseEntity<>("basket", HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>("책 또는 회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
        }

//        basketService.save(isbn, loginEmail);
//        return new ResponseEntity("basket", HttpStatus.OK);

    }
}