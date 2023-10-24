package com.icia.book.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BookDTO {

    private Long id;
    private String isbn;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private String bookDate;
    private String bookProfile;
    private int bookCount;
    private int bookStatus;
    private int bookPrice;
    private int bookSalePrice;
    private String bookContents;
    private Long categoryId;
}
