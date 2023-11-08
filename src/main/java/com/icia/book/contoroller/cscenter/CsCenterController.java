package com.icia.book.contoroller.cscenter;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.dto.FaqDTO;
import com.icia.book.service.CsCenterCategoryService;
import com.icia.book.service.FaqService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/csCenter")
public class CsCenterController {

    private final CsCenterCategoryService csCenterCategoryService;
    private final FaqService faqService;

    @GetMapping("/faq")
    public String findAll(Model model){
        List<CscenterCategoryDTO> csCenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("csCenterCategoryList", csCenterCategoryDTOList);
        return "csCenter/notice";
    }

    @GetMapping("/faq/list")
    public ResponseEntity findFaqList(@RequestParam("id") Long id, @RequestParam("page") int page) {
        Page<FaqDTO> faqDTOList = faqService.findAllById(id, page);
        return new ResponseEntity(faqDTOList, HttpStatus.OK);
    }

    @PutMapping("/faq/upHits")
    public ResponseEntity faqUpHits(@RequestBody Map<String, Long> requestBody){
        Long id = requestBody.get("id");
        faqService.upHits(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
