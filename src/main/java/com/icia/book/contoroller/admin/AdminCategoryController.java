package com.icia.book.contoroller.admin;

import com.icia.book.dto.CategoryDTO;
import com.icia.book.service.CategoryService;
import lombok.RequiredArgsConstructor;
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
    public String findAll(Model model) {
        List<CategoryDTO> categoryDTOList = categoryService.findAll();
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
        categoryService.update(categoryDTO);
        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity delete(@RequestBody CategoryDTO CategoryDTO) {
        categoryService.delete(CategoryDTO.getId());
        return new ResponseEntity(HttpStatus.OK);
    }
}
