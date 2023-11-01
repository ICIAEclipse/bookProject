package com.icia.book.contoroller.member;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.dto.OrderDTO;
import com.icia.book.service.MemberService;
import com.icia.book.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.NoSuchElementException;

@RequiredArgsConstructor
@RequestMapping("/member")
@Controller
public class MemberController {
    private final MemberService memberService;
    private final OrderService orderService;

    @GetMapping("/save")
    public String saveForm() {
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO) {
        try {
            memberService.save(memberDTO);
            return "redirect:/member/login";
        } catch (Exception e) {
            System.out.println(e.toString());
            return "redirect:/member/save";
        }
    }

    @PostMapping("duplicate-check")
    public ResponseEntity EmailDupleCheck(@RequestParam("memberEmail") String memberEmail) {
        try {
            memberService.findByMemberEmail(memberEmail);
            return new ResponseEntity(HttpStatus.CONFLICT);
        } catch (NoSuchElementException noSuchElementException) {
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/update/{id}")
    public String update(@PathVariable("id") Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        System.out.println("값이 넘어가는지 확인 " + memberDTO);
        return "memberPages/memberUpdate";
    }

    @PostMapping("/update")
    public String update(MemberDTO memberDTO) {
        System.out.println("dto 확인" + memberDTO);
        memberService.update(memberDTO);
        return "index";
    }


    @GetMapping("passCheck")
    public String passcheck() {
        return "memberPages/passCheck";
    }


    @PostMapping("/passCheck")
    public String passcheck(@ModelAttribute MemberDTO memberDTO, Model model) {
        System.out.println("포스트입니다 " + memberDTO);
        String getMemberEmail = memberDTO.getMemberEmail();
        String getPw = memberDTO.getMemberPassword();
        MemberDTO result = memberService.findByMemberEmail(getMemberEmail);
        System.out.println("비밀번호 체크입니다 : " + result);
        if (result.getMemberPassword().equals(getPw)) {
            model.addAttribute("member", result);
            return "/memberPages/memberUpdate";
        } else {
            return "index";
        }
    }


    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false, defaultValue = "") String error,
                            @RequestParam(value = "continued", required = false, defaultValue = "/") String continued,
                            Model model) {
        model.addAttribute("continued", continued);
        model.addAttribute("error", error);
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        @RequestParam(value = "continued", required = false, defaultValue = "/") String continued,
                        HttpSession session) {
        try {
            boolean result = memberService.login(memberDTO);
            if (result) {
                session.setAttribute("loginEmail", memberDTO.getMemberEmail());
                if (memberDTO.getMemberEmail().equals("admin")) {
                    return "redirect:/admin";
                } else {
                    return "redirect:"+continued;
                }
            } else {
                return "redirect:/member/login?error=error1&continued="+continued;
            }
        } catch (Exception e) {
            return "redirect:/member/login?error=error1&continued="+continued;
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute("loginEmail");
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(HttpSession session,
                         Model model) {
        MemberDTO memberDTO = memberService.findByMemberEmail((String) session.getAttribute("loginEmail"));
        model.addAttribute("member", memberDTO);
        return "memberPages/mypage";
    }


    @GetMapping("/passcheck")
    public String passCheck() {
        return "passCheck";
    }

    @PostMapping("/passcheck")
    public String passCheck(MemberDTO memberDTO, Model model) {
        Boolean result = memberService.login(memberDTO);
        if (result == null) {
            return "index";
        } else {
            model.addAttribute("member", memberDTO);
            return "/memberPages/memberUpdate";
        }
    }

    @GetMapping("/address")
    public String addressForm(HttpSession session,
                              Model model,
                              @RequestParam(value = "page", required = false, defaultValue = "1") int page) {
        String memberEmail = (String) session.getAttribute("loginEmail");
        Page<AddressDTO> addressDTOPage = memberService.findAddressByMemberEmail(memberEmail,page);

        int blockLimit = 5;
        int startPage = 1;
        int endPage = addressDTOPage.getTotalPages();
        if(addressDTOPage.getTotalPages() >= blockLimit){
            if(page+(blockLimit/2) <= addressDTOPage.getTotalPages()){
                endPage = page+(blockLimit/2);
            }
            startPage= endPage-(blockLimit-1);
            if(page-(blockLimit/2)<1){
                startPage = 1;
                endPage = 5;
            }
        }
        model.addAttribute("addressList", addressDTOPage);
        model.addAttribute("page", page);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "memberPages/memberAddress";
    }

    @PostMapping("/address")
    public ResponseEntity saveAddress(@RequestBody AddressDTO addressDTO,
                                      HttpSession session,
                                      @RequestParam("defaultAddressChecked") boolean defaultAddressChecked){
        String memberEmail = (String) session.getAttribute("loginEmail");
        memberService.saveAddress(addressDTO, memberEmail, defaultAddressChecked);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/address")
    public ResponseEntity deleteAddress(@RequestParam("addressId") Long addressId,
                                        HttpSession session){
        String memberEmail = (String) session.getAttribute("loginEmail");
        boolean result = memberService.deleteAddress(addressId, memberEmail);
        if(result){
            return new ResponseEntity<>(HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/address/default")
    public ResponseEntity setDefaultAddress(HttpSession session,
                                            @RequestParam("addressId") Long addressId){
        String memberEmail = (String) session.getAttribute("loginEmail");
        memberService.setDefaultAddress(memberEmail, addressId);
        Page<AddressDTO> addressDTOPage = memberService.findAddressByMemberEmail(memberEmail,1);
        return new ResponseEntity<>(addressDTOPage,HttpStatus.OK);
    }

    @GetMapping("/delete")
    public String delete(MemberDTO memberDTO, Model model) {
        MemberDTO memberDTO1 = memberService.findById(memberDTO.getId());
        System.out.println("여기는 딜리트의 계곡입니다. " + memberDTO1);
        model.addAttribute("member", memberDTO1);
        return "memberPages/memberDelete";
    }

    @PostMapping("delete")
    public String delete(MemberDTO memberDTO) {
        memberService.update(memberDTO);
        return "redirect:/member/logout";
    }

    @GetMapping("/order")
    public String orderList(HttpSession session,
                            Model model){
        MemberDTO memberDTO = memberService.findByMemberEmail((String) session.getAttribute("loginEmail"));
        model.addAttribute("member", memberDTO);
        List<OrderDTO> orderDTOList = orderService.findAllByMemberEntity(memberDTO.getId());
        model.addAttribute("orderList", orderDTOList);
        return "memberPages/orderList";
    }

}

