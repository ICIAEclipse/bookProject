package com.icia.book.contoroller.cart;

import com.icia.book.dto.BookDTO;
import com.icia.book.dto.CartDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.service.CartService;
import com.icia.book.service.MemberService;
import lombok.Getter;
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
    public String save(@RequestBody CartDTO cartDTO,
                       HttpSession session){
        MemberDTO memberDTO = memberService.findByMemberEmail((String) session.getAttribute("loginEmail"));
        cartDTO.setMemberId(memberDTO.getId());
        System.out.println(cartDTO);
        cartService.save(cartDTO);
        return "redirect:/cart";
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
