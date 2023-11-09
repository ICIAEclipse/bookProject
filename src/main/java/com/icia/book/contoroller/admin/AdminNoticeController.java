package com.icia.book.contoroller.admin;

import com.icia.book.dto.NoticeDTO;
import com.icia.book.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/notice")
public class AdminNoticeController {

    private final NoticeService noticeService;

    @GetMapping
    public String findAll(@RequestParam(value = "q", required = false, defaultValue = "") String q,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          Model model) {
        Page<NoticeDTO> noticeDTOPage = noticeService.findAll(q, page);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < noticeDTOPage.getTotalPages()) ? startPage + blockLimit - 1 : noticeDTOPage.getTotalPages();
        model.addAttribute("noticeList", noticeDTOPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("q", q);
        return "adminPages/noticeList";
    }

    @GetMapping("/save")
    public String saveForm() {
        return "adminPages/noticeSave";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute NoticeDTO noticeDTO) {
        noticeService.save(noticeDTO);
        return "redirect:/admin/notice";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable("id") Long id, Model model) {
        NoticeDTO noticeDTO = noticeService.findById(id);
        model.addAttribute("notice", noticeDTO);
        return "adminPages/noticeUpdate";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute NoticeDTO noticeDTO, @RequestParam(name = "deleteFile", required = false) List<String> deleteFileList) throws IOException {
        noticeService.update(noticeDTO, deleteFileList);
        return "redirect:/admin/notice";
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable("id") Long id) {
        noticeService.delete(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
