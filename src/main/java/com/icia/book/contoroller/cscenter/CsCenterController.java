package com.icia.book.contoroller.cscenter;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.dto.FaqDTO;
import com.icia.book.dto.NoticeDTO;
import com.icia.book.service.CsCenterCategoryService;
import com.icia.book.service.FaqService;
import com.icia.book.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
@RequestMapping("/csCenter")
public class CsCenterController {

    private final CsCenterCategoryService csCenterCategoryService;
    private final FaqService faqService;

    private final NoticeService noticeService;

    @GetMapping
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

    @GetMapping("/notice")
    public ResponseEntity noticeList(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                                     @RequestParam(value = "page", required = false, defaultValue = "1") int page){
        Page<NoticeDTO> noticeDTOPage = noticeService.findAll(q, page);
        return new ResponseEntity(noticeDTOPage, HttpStatus.OK);
    }

    @GetMapping("/notice/{id}")
    public String noticeDetail(@PathVariable("id") Long id,
                               Model model){
        NoticeDTO noticeDTO = noticeService.findById(id);
        model.addAttribute("notice", noticeDTO);
        return "csCenter/noticeDetail";
    }
}
