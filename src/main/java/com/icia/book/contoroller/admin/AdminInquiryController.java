package com.icia.book.contoroller.admin;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.dto.InquiryCommentDTO;
import com.icia.book.dto.InquiryDTO;
import com.icia.book.service.CsCenterCategoryService;
import com.icia.book.service.InquiryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/inquiry")
public class AdminInquiryController {

    private final InquiryService inquiryService;
    private final CsCenterCategoryService csCenterCategoryService;

    @GetMapping
    public String findAll(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                          Model model) {
        Page<InquiryDTO> inquiryDTOPage = inquiryService.findAll(page, status);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < inquiryDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : inquiryDTOPage.getTotalPages();
        model.addAttribute("inquiryList", inquiryDTOPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "adminPages/adminInquiryList";
    }

    @GetMapping("/{id}")
    public String detail(@PathVariable("id") Long id, Model model){
        InquiryDTO inquiryDTO = inquiryService.findById(id);
        List<CscenterCategoryDTO> cscenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("inquiry", inquiryDTO);
        model.addAttribute("centerCategory", cscenterCategoryDTOList);
        return "adminPages/adminInquiryComment";
    }

    @PostMapping()
    public String comment_save(@ModelAttribute InquiryCommentDTO inquiryCommentDTO){
        inquiryService.commentSave(inquiryCommentDTO);
        return "redirect:/admin/inquiry";
    }
}
