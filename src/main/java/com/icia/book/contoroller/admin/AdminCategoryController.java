package com.icia.book.contoroller.admin;

import com.icia.book.dto.CategoryDTO;
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
@RequestMapping("/admin/category")
public class AdminCategoryController {

    private final CategoryService categoryService;

    @GetMapping
    public String findAll(Model model,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          @RequestParam(value = "type", required = false, defaultValue = "categoryName") String type,
                          @RequestParam(value = "q", required = false, defaultValue = "") String q) {
        Page<CategoryDTO> categoryDTOList = categoryService.findAll(page, type, q);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < categoryDTOList.getTotalPages()) ? startPage + blockLimit - 1 : categoryDTOList.getTotalPages();
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("type", type);
        model.addAttribute("q", q);
        model.addAttribute("categoryList", categoryDTOList);
        return "adminPages/categoryList";
    }

    @PostMapping
    public ResponseEntity save(@RequestBody CategoryDTO categoryDTO) {
        Long id = categoryService.save(categoryDTO);
        if (id != null) {
            return new ResponseEntity(HttpStatus.OK);
        } else {
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
    }

    @PutMapping
    public ResponseEntity update(@RequestBody CategoryDTO categoryDTO) {
        /**
         * 미분류 카테고리는 수정 불가
         */
        if(categoryDTO.getCategoryId().equals("000")){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        categoryService.update(categoryDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody CategoryDTO categoryDTO) {
        /**
         * 미분류 카테고리는 삭제 불가
         */
        if(categoryDTO.getCategoryId().equals("000")){
            return new ResponseEntity(HttpStatus.CONFLICT);
        }
        categoryService.delete(categoryDTO.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
