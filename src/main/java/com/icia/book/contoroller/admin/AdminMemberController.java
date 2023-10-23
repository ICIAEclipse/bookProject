package com.icia.book.contoroller.admin;

import com.icia.book.dto.MemberDTO;
import com.icia.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMemberController {
    public final MemberService memberService;

    @GetMapping("/member")
    public String findAll(Model model){
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "adminPages/memberList";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "adminPages/memberDetail";
    }
}
