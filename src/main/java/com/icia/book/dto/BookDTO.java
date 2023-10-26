package com.icia.book.dto;


import com.icia.book.entity.BookEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static BookDTO toDTO(BookEntity bookEntity){
        BookDTO bookDTO = new BookDTO();
        bookDTO.setId(bookEntity.getId());
        bookDTO.setIsbn(bookEntity.getIsbn());
        bookDTO.setBookName(bookEntity.getBookName());
        bookDTO.setBookAuthor(bookEntity.getBookAuthor());
        bookDTO.setBookPublisher(bookEntity.getBookPublisher());
        bookDTO.setBookDate(bookEntity.getBookDate());
        bookDTO.setBookProfile(bookEntity.getBookProfile());
        bookDTO.setBookCount(bookEntity.getBookCount());
        bookDTO.setBookStatus(bookEntity.getBookStatus());
        bookDTO.setBookPrice(bookEntity.getBookPrice());
        bookDTO.setBookSalePrice(bookEntity.getBookSalePrice());
        bookDTO.setBookContents(bookEntity.getBookContents());
        return bookDTO;
    }
}
