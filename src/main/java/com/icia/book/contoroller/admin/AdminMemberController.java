package com.icia.book.contoroller.admin;

import com.icia.book.dto.MemberDTO;
import com.icia.book.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminMemberController {
    public final MemberService memberService;

    @GetMapping("/member")
    public String findAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "type", required = false, defaultValue = "memberEmail") String type,
                          @RequestParam(value = "q", required = false, defaultValue = "") String q,
                          Model model){
        Page<MemberDTO> memberDTOList = memberService.findAll(page, type, q);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < memberDTOList.getTotalPages()) ? startPage + blockLimit - 1 : memberDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute( "endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        model.addAttribute("memberList", memberDTOList);
        return "adminPages/memberList";
    }

    @GetMapping("/member/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "adminPages/memberDetail";
    }

    @PutMapping("/member/{id}")
    public ResponseEntity statusUpdate(@RequestBody MemberDTO memberDTO){
        System.out.println("memberDTO = " + memberDTO);
        memberService.statusUpdate(memberDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/member/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id){
        memberService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
