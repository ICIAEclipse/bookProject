package com.icia.book.entity;

import com.icia.book.dto.BookDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "book_table")
@Entity
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, unique = true, nullable = false)
    private String isbn;

    @Column(length = 500, nullable = false)
    private String bookName;

    @Column(length = 100, nullable = false)
    private String bookAuthor;

    @Column(length = 20, nullable = false)
    private String bookPublisher;

    @Column(length = 20)
    private String bookDate;

    @Column(length = 1000)
    private String bookProfile;

    @Column
    private int bookCount;

    @Column
    private int bookStatus;

    @Column
    private int bookPrice;

    @Column
    private int bookSalePrice;

    @Column(length = 500)
    private String bookContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_category_id")
    private CategoryEntity categoryEntity;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetailEntity;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BasketEntity> basketEntityList;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<RecentEntity> recentEntityList;

    public static BookEntity toSaveBookEntity(BookDTO bookDTO, CategoryEntity categoryEntity) {
        BookEntity bookEntity = new BookEntity();
        bookEntity.setIsbn(bookDTO.getIsbn());
        bookEntity.setBookName(bookDTO.getBookName());
        bookEntity.setBookAuthor(bookDTO.getBookAuthor());
        bookEntity.setBookPublisher(bookDTO.getBookPublisher());
        bookEntity.setBookDate(bookDTO.getBookDate());
        bookEntity.setBookProfile(bookDTO.getBookProfile());
        bookEntity.setBookCount(bookDTO.getBookCount());
        if (bookDTO.getBookCount() == 0) {
            bookEntity.setBookStatus(0);
        } else {
            bookEntity.setBookStatus(1);
        }
        bookEntity.setBookPrice(bookDTO.getBookPrice());
        bookEntity.setBookSalePrice(bookDTO.getBookSalePrice());
        bookEntity.setBookContents(bookDTO.getBookContents());
        bookEntity.setCategoryEntity(categoryEntity);
        return bookEntity;
    }

    public static BookEntity toDeleteCategoryBookEntity(BookEntity bookEntity, CategoryEntity categoryEntity) {
        BookEntity book = new BookEntity();
        book.setId(bookEntity.getId());
        book.setIsbn(bookEntity.getIsbn());
        book.setBookName(bookEntity.getBookName());
        book.setBookAuthor(bookEntity.getBookAuthor());
        book.setBookPublisher(bookEntity.getBookPublisher());
        book.setBookDate(bookEntity.getBookDate());
        book.setBookProfile(bookEntity.getBookProfile());
        book.setBookCount(bookEntity.getBookCount());
        book.setBookStatus(bookEntity.getBookStatus());
        book.setBookPrice(bookEntity.getBookPrice());
        book.setBookSalePrice(bookEntity.getBookSalePrice());
        book.setBookContents(bookEntity.getBookContents());
        book.setCategoryEntity(categoryEntity);
        return book;
    }

    public static BookEntity toUpdateEntity(BookEntity bookEntity, BookDTO bookDTO, CategoryEntity categoryEntity) {
        BookEntity updateEntity = bookEntity;
        updateEntity.setBookStatus(bookDTO.getBookStatus());
        updateEntity.setBookCount(bookDTO.getBookCount());
        updateEntity.setCategoryEntity(categoryEntity);
        return updateEntity;
    }

    public static BookEntity toSaveEntity(BookDTO bookDTO) {
        BookEntity booksEntity = new BookEntity();
        booksEntity.setId(bookDTO.getId());
        booksEntity.setIsbn(bookDTO.getIsbn());
        booksEntity.setBookName(bookDTO.getBookName());
        booksEntity.setBookAuthor(bookDTO.getBookAuthor());
        booksEntity.setBookPublisher(bookDTO.getBookPublisher());
        booksEntity.setBookDate(bookDTO.getBookDate());
        booksEntity.setBookProfile(bookDTO.getBookProfile());
        booksEntity.setBookCount(bookDTO.getBookCount());
        booksEntity.setBookPrice(bookDTO.getBookPrice());
        booksEntity.setBookSalePrice(bookDTO.getBookSalePrice());
        booksEntity.setBookContents(bookDTO.getBookContents());
        return booksEntity;
    }
}
