package com.icia.book.dto;


import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CommentEntity;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

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
    private List<CommentDTO> commentDTOList = new ArrayList<>();
    private double bookScore;


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
    public static BookDTO toDTO(BookEntity bookEntity, List<CommentEntity> commentEntityList){
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
        double sum = 0;
        for(CommentEntity commentEntity: commentEntityList){
            CommentDTO commentDTO = CommentDTO.toDTO(commentEntity);
            sum += commentEntity.getCommentScore();
            bookDTO.getCommentDTOList().add(commentDTO);
        }
        sum = Math.round((sum / commentEntityList.size()) * 10) / 10.0;
        bookDTO.setBookScore(sum);
        return bookDTO;
    }
}
