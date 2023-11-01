package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BasketDTO {
    private Long id;
    private String isbn;
    private String memberEmail;
}
