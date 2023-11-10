package com.icia.book.contoroller.member;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.dto.InquiryDTO;
import com.icia.book.service.CsCenterCategoryService;
import com.icia.book.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/csCenter/inquiry")
public class InquiryController {

    private final InquiryService inquiryService;
    private final CsCenterCategoryService csCenterCategoryService;

    @GetMapping()
    public String findAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          HttpSession session, Model model){
        String loginMember = (String) session.getAttribute("loginEmail");
        Page<InquiryDTO> inquiryDTOPage = inquiryService.memberInquiryList(loginMember, page);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < inquiryDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : inquiryDTOPage.getTotalPages();
        model.addAttribute("inquiryList", inquiryDTOPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        return "memberPages/inquiryList";
    }

    @GetMapping("/save")
    public String saveForm(Model model){
        List<CscenterCategoryDTO> cscenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("centerCategory", cscenterCategoryDTOList);
        return "memberPages/inquirySave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute InquiryDTO inquiryDTO){
        System.out.println("inquiryDTO = " + inquiryDTO);
        inquiryService.save(inquiryDTO);
        return "redirect:/csCenter/inquiry";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        InquiryDTO inquiryDTO = inquiryService.findById(id);
        List<CscenterCategoryDTO> cscenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("inquiry", inquiryDTO);
        model.addAttribute("centerCategory", cscenterCategoryDTOList);
        return "memberPages/inquiryDetail";
    }

    @PutMapping("/{id}")
    public ResponseEntity cancelInquiry(@PathVariable("id") Long id){
        inquiryService.cancel(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
