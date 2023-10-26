package com.icia.book.contoroller.admin;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.icia.book.dto.AladinSearchDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.service.AladinService;
import com.icia.book.service.BookService;
import com.icia.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/book")
public class AdminBookController {

    private final AladinService aladinService;
    private final CategoryService categoryService;
    private final BookService bookService;

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
        return "adminPages/bookList";
    }
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

    @PostMapping()
    public ResponseEntity save(@RequestBody BookDTO bookDTO){
        boolean save = bookService.save(bookDTO);
        if(save){
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody BookDTO bookDTO){
        bookService.update(bookDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
