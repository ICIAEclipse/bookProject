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
    public String saveForm(){
        return "memberPages/memberSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        try{
            memberService.save(memberDTO);
            return "redirect:/member/login";
        }catch (Exception e){
            System.out.println(e.toString());
            return "redirect:/member/save";
        }
    }

    @PostMapping("duplicate-check")
    public ResponseEntity EmailDupleCheck(@RequestParam("memberEmail") String memberEmail){
        try {
            memberService.findByMemberEmail(memberEmail);
            return new ResponseEntity(HttpStatus.CONFLICT);
        }catch(NoSuchElementException noSuchElementException){
            return new ResponseEntity(HttpStatus.OK);
        }
    }

    @GetMapping("/update")
    public String update(Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }


    @GetMapping("/login")
    public String loginForm(@RequestParam(value = "error", required = false, defaultValue = "") String error,
                            Model model){
        model.addAttribute("error", error);
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
                        Model model,
                        HttpSession session){
        try{
            boolean result = memberService.login(memberDTO);
            if(result){
                session.setAttribute("loginEmail", memberDTO.getMemberEmail());
                if(memberDTO.getMemberEmail().equals("admin")){
                    return "redirect:/admin";
                }else {
                    return "redirect:/";
                }
            }else {
                return "redirect:/member/login?error=error1";
            }
        }catch (Exception e){
            return"redirect:/member/login?error=error1";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("loginEmail");
        return "redirect:/";
    }

    @GetMapping("/mypage")
    public String myPage(HttpSession session,
                         Model model){
        MemberDTO memberDTO = memberService.findByMemberEmail((String) session.getAttribute("loginEmail"));
        model.addAttribute("member", memberDTO);
        return "memberPages/mypage";
    }

    @GetMapping("/address")
    public String addressForm(HttpSession session,
                              Model model){
        String memberEmail = (String) session.getAttribute("loginEmail");
        List<AddressDTO> addressDTOList = memberService.findAddressByMemberEmail(memberEmail);
        System.out.println(addressDTOList);
        model.addAttribute("addressList", addressDTOList);
        return "memberPages/memberAddress";
    }
}
