package com.icia.book.contoroller.member;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.service.MemberService;
import lombok.RequiredArgsConstructor;
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
                            Model model) {
        model.addAttribute("error", error);
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        Model model,
                        HttpSession session) {
        try {
            boolean result = memberService.login(memberDTO);
            if (result) {
                session.setAttribute("loginEmail", memberDTO.getMemberEmail());
                session.setAttribute("loginId", memberDTO.getId());
                if (memberDTO.getMemberEmail().equals("admin")) {
                    return "redirect:/admin";
                } else {
                    return "redirect:/";
                }
            } else {
                return "redirect:/member/login?error=error1";
            }
        } catch (Exception e) {
            return "redirect:/member/login?error=error1";
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
    public String addressForm(HttpSession session, Model model) {
        String memberEmail = (String) session.getAttribute("loginEmail");
        List<AddressDTO> addressDTOList = memberService.findAddressByMemberEmail(memberEmail);
        model.addAttribute("addressList", addressDTOList);
        return "memberPages/memberAddress";
    }

    @PostMapping("/address")
    public ResponseEntity saveAddress(@RequestBody AddressDTO addressDTO,
                                      @RequestParam("memberEmail") String memberEmail,
                                      @RequestParam("defaultAddressChecked") boolean defaultAddressChecked){
        memberService.saveAddress(addressDTO, memberEmail, defaultAddressChecked);
        List<AddressDTO> addressDTOList = memberService.findAddressByMemberEmail(memberEmail);
        return new ResponseEntity<>(addressDTOList,HttpStatus.OK);
    }

    @PostMapping("/address/default")
    public ResponseEntity setDefaultAddress(@RequestParam("memberEmail") String memberEmail,
                                            @RequestParam("addressId") Long addressId){
        memberService.setDefaultAddress(memberEmail, addressId);
        List<AddressDTO> addressDTOList = memberService.findAddressByMemberEmail(memberEmail);
        return new ResponseEntity<>(addressDTOList,HttpStatus.OK);
    }

    @GetMapping("/delete")
    public String delete(MemberDTO memberDTO){
        return "memberPages/memberDelete";
    }

}

