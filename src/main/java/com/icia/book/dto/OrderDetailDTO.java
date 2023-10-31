package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class OrderDetailDTO {
    private Long id;
    private String bookProfile;
    private String bookName;

    private int bookCount;

    private int bookPrice;

    private Long bookId;
    private Long orderId;
    private Long memberId;


}
