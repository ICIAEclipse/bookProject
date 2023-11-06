package com.icia.book.contoroller.member;

import com.icia.book.dto.*;
import com.icia.book.service.MemberService;
import com.icia.book.service.OrderService;
import com.icia.book.service.PayService;
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
    private final PayService payService;

    @PostMapping("/createNumber")
    public ResponseEntity createNumber(@RequestBody OrderRequestDTO orderRequestDTO,
                                       HttpSession session){
        while(true){
            String orderCode = "O" + System.currentTimeMillis();
            boolean result = orderService.findByOrderCode(orderCode);
            if(result){
                String memberEmail = (String) session.getAttribute("loginEmail");
//                AddressDTO defaultAddressDTO = memberService.findDefaultAddressByMemberEmail(memberEmail);
//                List<OrderDetailDTO> orderDetailDTOList = orderService.findAllByBookIds(orderRequestDTO.getBooKIdList(), orderRequestDTO.getCountList());
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
            model.addAttribute("orderDetailList", orderDetailDTOList);
        }
        if(defaultAddressDTO !=null){
            model.addAttribute("defaultAddress", defaultAddressDTO);
        }
        model.addAttribute("orderCode", orderCode);
        return "orderPages/order";
    }

    @PostMapping("/order")
    public ResponseEntity payment(@RequestBody OrderDTO orderDTO,
                                  @RequestParam("memberEmail") String memberEmail,
                                  HttpSession session){
        if(memberEmail.equals(session.getAttribute("loginEmail"))){
            System.out.println(orderDTO.getOrderDetailDTOList().get(0).getBookProfile());

            System.out.println(orderDTO.getOrderStatus());
            boolean result = orderService.checkCount(orderDTO);
            if(result){
                orderService.saveOrder(orderDTO, memberEmail);
                return new ResponseEntity(HttpStatus.OK);
            }else{
                return new ResponseEntity("countless" ,HttpStatus.BAD_REQUEST);
            }
        }else {
            return new ResponseEntity("not_equal_purchaser",HttpStatus.CONFLICT);
        }
    }

    /**
     * 결제요청
     */
    @PostMapping("/pay/kakaoPay")
    public ResponseEntity kakaoPayReady(@RequestBody KakaoPayReadyRequestDTO kakaoPayReadyRequestDTO,
                                        HttpSession session,
                                        Model model){
        String partNerUserId = (String) session.getAttribute("loginEmail");
        KakaoPayReadyResponseDTO kakaoPayReadyResponseDTO = payService.kakaoPayReady(kakaoPayReadyRequestDTO, partNerUserId);


        model.addAttribute("kakaoPayApprovalRequestDTO", kakaoPayReadyResponseDTO);

        return new ResponseEntity<>(kakaoPayReadyResponseDTO,HttpStatus.OK);
    }

    /**
     * 결제 요청 성공
     */
    @GetMapping("/pay/success")
    public String afterPayRequest(@RequestParam("pg_token") String pgToken,
                                    @ModelAttribute KakaoPayApproveRequestDTO kakaoPayApproveRequestDTO,
                                  Model model) {
        System.out.println(kakaoPayApproveRequestDTO);

        KakaoPayApproveResponseDTO kakaoPayApproveResponseDTO = payService.kakaoPayApprove(kakaoPayApproveRequestDTO, pgToken);
        model.addAttribute("kakaoPayApprove", kakaoPayApproveResponseDTO);
        model.addAttribute("payment", "success");
        return "orderPages/successPay";
    }

    /**
     * 결제 진행 중 취소
     */
    @GetMapping("/pay/cancel")
    public String cancel(Model model) {
        model.addAttribute("payment", "cancel");
        return "orderPages/successPay";
    }

    /**
     * 결제 실패
     */
    @GetMapping("/pay/fail")
    public String fail(Model model) {
        model.addAttribute("payment", "fail");
        return "orderPages/successPay";
    }
}
