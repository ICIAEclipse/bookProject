package com.icia.book.contoroller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.book.dto.AladinSearchDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.service.AladinService;
import com.icia.book.service.BookService;
import com.icia.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/book")
public class AdminBookController {

    private final AladinService aladinService;
    private final CategoryService categoryService;
    private final BookService bookService;
    @GetMapping("/save")
    public String aladinList(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                             @RequestParam(value = "type", required = false, defaultValue = "Keyword") String type,
                             @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             Model model) throws JsonProcessingException {
        AladinSearchDTO aladinSearchDTO = null;
        int pageLimit = 10;
        if(q.equals("")){
            aladinSearchDTO = aladinService.allList(page);
        } else {
            aladinSearchDTO = aladinService.searchList(page, type, q);
        }
        int maxPages = (int)(Math.ceil((double)aladinSearchDTO.getTotalResults()/pageLimit));
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < maxPages) ? startPage + blockLimit - 1 : maxPages;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        model.addAttribute("maxPages", maxPages);
        model.addAttribute("aladinSearch", aladinSearchDTO);
        model.addAttribute("categoryList", categoryService.findAll());
        return "adminPages/bookSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BookDTO bookDTO){
        System.out.println("bookDTO = " + bookDTO);
        bookService.save(bookDTO);
        return "redirect:/admin/book";
    }
}
