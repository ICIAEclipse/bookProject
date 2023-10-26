package com.icia.book.contoroller.member;

import com.icia.book.dto.BookDTO;
import com.icia.book.service.BookService;
import com.icia.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public class BookController {
    private final BookService bookService;
    private final CategoryService categoryService;

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


}
