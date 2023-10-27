package com.icia.book.contoroller.admin;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.service.CsCenterCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminCsCenterController {

    private final CsCenterCategoryService csCenterCategoryService;

    @GetMapping("/cscenter/category")
    public String findAll(Model model){
        List<CscenterCategoryDTO> cscenterCategoryDTOList = csCenterCategoryService.findAll();
        model.addAttribute("csCenterCategoryList", cscenterCategoryDTOList);
        return "adminPages/csCategoryList";
    }

    @PostMapping("/cscenter/category")
    public ResponseEntity save(@RequestBody CscenterCategoryDTO cscenterCategoryDTO){
        int save = csCenterCategoryService.save(cscenterCategoryDTO);
        if(save == 0)
            return new ResponseEntity(HttpStatus.OK);
        else if(save == 1)
            return new ResponseEntity("이미사용중인 아이디입니다.",HttpStatus.CONFLICT);
        else {
            return new ResponseEntity("이미사용중인 카테고리 이름입니다.",HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/cscenter/category")
    public ResponseEntity update(@RequestBody CscenterCategoryDTO cscenterCategoryDTO){
        int save = csCenterCategoryService.update(cscenterCategoryDTO);
        if(save == 0)
            return new ResponseEntity(HttpStatus.OK);
        else if(save == 1)
            return new ResponseEntity("이미사용중인 아이디입니다.",HttpStatus.CONFLICT);
        else {
            return new ResponseEntity("이미사용중인 카테고리 이름입니다.",HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/cscenter/category")
    public ResponseEntity delete(@RequestBody CscenterCategoryDTO cscenterCategoryDTO){
        csCenterCategoryService.delete(cscenterCategoryDTO);
        return new ResponseEntity(HttpStatus.OK);
    }
}
