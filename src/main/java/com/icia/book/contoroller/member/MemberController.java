package com.icia.book.contoroller.member;

import com.icia.book.dto.MemberDTO;
import com.icia.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

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

    @GetMapping("/update")
    public String update(Long id, Model model) {
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "memberPages/memberUpdate";
    }


    @GetMapping("/login")
    public String loginForm(){
        return "memberPages/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute MemberDTO memberDTO,
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
                return "redirect:/member/login";
            }
        }catch (Exception e){
            return"redirect:/member/login";
        }
    }
}
