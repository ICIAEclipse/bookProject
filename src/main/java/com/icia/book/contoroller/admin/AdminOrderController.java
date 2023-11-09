package com.icia.book.contoroller.admin;

import com.icia.book.dto.OrderDTO;
import com.icia.book.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
@RequiredArgsConstructor
@RequestMapping("/admin/order")
public class AdminOrderController {

    private final OrderService orderService;

    @GetMapping
    public String findAll(@RequestParam(value = "status", required = false, defaultValue = "-1") int status,
                          @RequestParam(value = "page", required = false, defaultValue = "1") int page,
                          Model model){
        Page<OrderDTO> orderDTOS = orderService.findAllByStatus(status, page);
        int blockLimit = 3;
        int startPage = (((int) (Math.ceil((double) page / blockLimit))) - 1) * blockLimit + 1;
        int endPage = ((startPage + blockLimit - 1) < orderDTOS.getTotalPages()) ? startPage + blockLimit - 1 : orderDTOS.getTotalPages();
        model.addAttribute("orderList", orderDTOS);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("page", page);
        model.addAttribute("status", status);
        return "adminPages/adminOrderList";
    }

    @PutMapping
    public ResponseEntity updateStatus(@RequestBody HashMap<String, Object> req){
        Long id = Long.valueOf(req.get("id").toString());
        int status = Integer.parseInt(req.get("status").toString());
        orderService.updateStatus(id, status);
        return new ResponseEntity(HttpStatus.OK);
    }
}
