package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class OrderRequestDTO {
    private List<Long> booKIdList;
    private List<Integer> countList;
    private String memberEmail;
    private String orderCode;
}
