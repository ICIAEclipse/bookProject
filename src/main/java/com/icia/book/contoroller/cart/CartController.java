package com.icia.book.contoroller.cart;

import com.icia.book.dto.CartDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.service.CartService;
import com.icia.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;
    private final MemberService memberService;

    @GetMapping("/save")
    public String saveForm(){
        return "redirect:/cart";
    }

    @PostMapping("/save")
    public ResponseEntity save(@RequestBody CartDTO cartDTO,
                       HttpSession session){
        MemberDTO memberDTO = memberService.findByMemberEmail((String) session.getAttribute("loginEmail")); // 세션에 있는 이메일 값을 memberDTO로 저장
        cartDTO.setMemberId(memberDTO.getId()); // 멤버아이디를(DTO) cartDTO로 진짜 만듦
        System.out.println(cartDTO);
        boolean result = cartService.save(cartDTO); // 멤버 아이디랑 북 아이디를 카트서비스에 저장
        if (result==true) {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping
    public String findAll(Model model,HttpSession session){
        List<CartDTO> cartDTOList = cartService.findAll((String) session.getAttribute("loginEmail"));
        model.addAttribute("cartList", cartDTOList);
    return "cartPages/cartList";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cartService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
