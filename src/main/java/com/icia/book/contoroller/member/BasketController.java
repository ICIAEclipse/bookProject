package com.icia.book.contoroller.member;

import com.icia.book.dto.BasketDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.service.BasketService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("/basket")
@Controller
public class BasketController {
    private final BasketService basketService;


    @GetMapping("/list")
    public String basketFindAll(HttpSession session, Model model) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        List<BasketDTO> basketDTOList = basketService.findAll(loginEmail);
        model.addAttribute("basketList", basketDTOList);
        System.out.println(basketDTOList.toString());
        return "/bookPages/basketList";
    }

    @PostMapping
    public ResponseEntity basket(@RequestBody Map<String, String> req, HttpSession session) {
        String loginEmail = (String) session.getAttribute("loginEmail");
        String isbn = req.get("isbn");
        System.out.println("이상하네" + isbn + loginEmail);
        boolean result = basketService.save(isbn, loginEmail);
        System.out.println("값은? " + result);
        if(result == true) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }

    }
}


