package com.icia.book.contoroller.cscenter;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.service.CsCenterCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/csCenter")
public class CsCenterController {

    private final CsCenterCategoryService csCenterCategoryService;

    @GetMapping("/faq")
    public String findAll(Model model){
        List<CscenterCategoryDTO> csCenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("csCenterCategoryList", csCenterCategoryDTOList);
        return "csCenter/notice";
    }
}
