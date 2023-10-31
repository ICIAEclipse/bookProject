package com.icia.book.contoroller.member;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.dto.OrderDetailDTO;
import com.icia.book.dto.OrderRequestDTO;
import com.icia.book.service.BookService;
import com.icia.book.service.MemberService;
import com.icia.book.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;


@RequiredArgsConstructor
@Controller
@RequestMapping("/order")
public class OrderController {
    private final OrderService orderService;
    private final MemberService memberService;
    private final BookService bookService;

    @PostMapping("/createNumber")
    public ResponseEntity createNumber(@RequestBody OrderRequestDTO orderRequestDTO,
                                       HttpSession session){
        while(true){
            String orderCode = "O" + System.currentTimeMillis();
            boolean result = orderService.findByOrderCode(orderCode);
            if(result){
                orderRequestDTO.setOrderCode(orderCode);
                orderRequestDTO.setMemberEmail((String) session.getAttribute("loginEmail"));
                return new ResponseEntity<>(orderRequestDTO, HttpStatus.OK);
            }
        }
    }

    @GetMapping("/order")
    public String orderForm(@RequestParam("booKIdList") List<Long> bookIdList,
                            @RequestParam("countList") List<Integer> countList,
                            @RequestParam("orderCode") String orderCode,
                            HttpSession session,
                            Model model){

        String memberEmail = (String) session.getAttribute("loginEmail");
        MemberDTO memberDTO = memberService.findByMemberEmail(memberEmail);
        AddressDTO defaultAddressDTO = memberService.findDefaultAddressByMemberEmail(memberEmail);
//        List<Long> bookIdList = orderRequestDTO.getBooKIdList();
        List<OrderDetailDTO> orderDetailDTOList = orderService.findAllByBookIds(bookIdList, countList);

        if(memberDTO !=null){
            model.addAttribute("member", memberDTO);
        }
        if(!orderDetailDTOList.isEmpty()){
            model.addAttribute("orderList", orderDetailDTOList);
        }
        if(defaultAddressDTO !=null){
            model.addAttribute("defaultAddress", defaultAddressDTO);
        }
        model.addAttribute("orderCode", orderCode);

        System.out.println(defaultAddressDTO);
        System.out.println(memberDTO);
        System.out.println(orderDetailDTOList);

        return "orderPages/order";
    }
}
