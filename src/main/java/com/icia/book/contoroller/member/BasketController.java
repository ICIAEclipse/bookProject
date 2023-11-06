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
    public ResponseEntity basket(@RequestBody BasketDTO basketDTO) {
        boolean result = basketService.save(basketDTO.getIsbn(), basketDTO.getMemberEmail());
        return new ResponseEntity<>(result, HttpStatus.OK);

//        try {
//            boolean check = basketService.findById(basketDTO.getIsbn(), basketDTO.getMemberEmail());
//            if (check == true) { // 있음
//                // DB에서 기존의 것 제거
//                basketService.delete(basketDTO.getIsbn(), basketDTO.getMemberEmail());
//                return new ResponseEntity<>("basket", HttpStatus.OK);
//            } else { // 없음
//                // DB에 추가
//                basketService.save(basketDTO.getIsbn(), basketDTO.getMemberEmail());
//                return new ResponseEntity<>("basket", HttpStatus.OK);
//            }
//
//
//        } catch (NoSuchElementException e) {
//            return new ResponseEntity<>("책 또는 회원을 찾을 수 없습니다.", HttpStatus.BAD_REQUEST);
//        }

//        basketService.save(isbn, loginEmail);
//        return new ResponseEntity("basket", HttpStatus.OK);

    }

    @GetMapping("/list")
    public ResponseEntity basketFindAll(@RequestBody String memberEmail) {
        System.out.println("확인 " + memberEmail);
        boolean result = true;
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
