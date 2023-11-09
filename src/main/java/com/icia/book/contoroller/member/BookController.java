package com.icia.book.contoroller.member;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.book.dto.AladinSearchDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.service.AladinService;
import com.icia.book.service.BookService;
import com.icia.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;
    private final AladinService aladinService;
    @GetMapping
    public String findAll(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                          @RequestParam(value = "categoryId", required = false, defaultValue = "") String categoryId,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          Model model){
        Page<BookDTO> bookDTOList = bookService.findAll(q, categoryId, page);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < bookDTOList.getTotalPages()) ? startPage + blockLimit - 1 : bookDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute( "endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("categoryId", categoryId);
        model.addAttribute("q", q);
        model.addAttribute("bookList", bookDTOList);
        model.addAttribute("categoryList", categoryService.findAll());
        return "bookPages/bookList";
    }


    @GetMapping("/bestseller")
    public String findBestseller(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             Model model) throws JsonProcessingException {
        int pageLimit = 10;
        AladinSearchDTO aladinSearchDTO = aladinService.bestSeller(page);
        int maxPages = (int)(Math.ceil((double)aladinSearchDTO.getTotalResults()/pageLimit));
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < maxPages) ? startPage + blockLimit - 1 : maxPages;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("maxPages", maxPages);
        model.addAttribute("aladinSearch", aladinSearchDTO);
        model.addAttribute("categoryList", categoryService.findAll());
        return "bookPages/bookBest";
    }

    @GetMapping("/new")
    public String findNew(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
                             Model model) throws JsonProcessingException {
        int pageLimit = 10;
        AladinSearchDTO aladinSearchDTO = aladinService.itemNewSpecial(page);
        int maxPages = (int)(Math.ceil((double)aladinSearchDTO.getTotalResults()/pageLimit));
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < maxPages) ? startPage + blockLimit - 1 : maxPages;
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("maxPages", maxPages);
        model.addAttribute("aladinSearch", aladinSearchDTO);
        model.addAttribute("categoryList", categoryService.findAll());
        return "bookPages/bookNew";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model){
        BookDTO bookDTO = bookService.findById(id);
        model.addAttribute("book", bookDTO);
        return "bookPages/bookDetail";
    }
}
