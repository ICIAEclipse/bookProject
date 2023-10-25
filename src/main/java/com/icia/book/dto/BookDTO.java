package com.icia.book.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Builder
public class BookDTO {

    private Long id;
    private String isbn;
    private String bookName;
    private String bookAuthor;
    private String bookPublisher;
    private String bookDate;
    private String bookProfile;
    private Integer bookCount;
    private Integer bookStatus;
    private Integer bookPrice;
    private Integer bookSalePrice;
    private String bookContents;
    private Long categoryId;
}
