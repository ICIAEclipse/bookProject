package com.icia.book.contoroller.cart;

import com.icia.book.dto.CartDTO;
import com.icia.book.service.CartService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {
    private final CartService cartService;

    @GetMapping("/save")
    public String saveForm(){
        return "cartPages/cartSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute CartDTO cartDTO){
        cartService.save(cartDTO);
        return "redirect:/cartPages/cartList";
    }

    @GetMapping
    public String findAll(Model model){
    List<CartDTO> cartDTOList = cartService.findAll();
    model.addAttribute("cartList", cartDTOList);
    return "cartPages/cartList";
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        cartService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
