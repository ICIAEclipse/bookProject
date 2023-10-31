package com.icia.book.contoroller.admin;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.dto.FaqDTO;
import com.icia.book.service.CsCenterCategoryService;
import com.icia.book.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/faq")
public class AdminFaqController {

    private final FaqService faqService;
    private final CsCenterCategoryService csCenterCategoryService;

    @GetMapping()
    public String findAll(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                          @RequestParam(value = "categoryId", required = false, defaultValue = "") Long categoryId,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page, Model model){
        Page<FaqDTO> faqDTOList = faqService.findAll(q, categoryId, page);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < faqDTOList.getTotalPages()) ? startPage + blockLimit - 1 : faqDTOList.getTotalPages();
        List<CscenterCategoryDTO> cscenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("cscenterCategoryList", cscenterCategoryDTOList);
        model.addAttribute("startPage", startPage);
        model.addAttribute( "endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("q", q);
        model.addAttribute("faqList", faqDTOList);
        return "adminPages/adminFaqList";
    }

    @GetMapping("/save")
    public String saveForm(Model model){
        List<CscenterCategoryDTO> cscenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("cscenterCategoryList", cscenterCategoryDTOList);
        return "adminPages/adminFaqSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute FaqDTO faqDTO){
        faqService.save(faqDTO);
        return "redirect:/admin/faq";
    }
}
