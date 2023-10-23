package com.icia.book.contoroller.member;

import com.icia.book.dto.MemberDTO;
import com.icia.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

            return "index";
        }catch (Exception e){
            System.out.println(e.toString());
            return "redirect:/member/save";
        }
    }

    @GetMapping("/login")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO){
        try{
            boolean result = memberService.login(memberDTO);
            if(result){
                if(memberDTO.getMemberEmail().equals("admin")){
                    return "redirect:/admin";
                }else {
                    return "redirect:/";
                }
            }else {
                return "redirect:/member/login";
            }
        }catch (Exception e){
            return"redirect:/member/login";
        }
    }
}
