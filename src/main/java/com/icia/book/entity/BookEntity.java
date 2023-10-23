package com.icia.book.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @Column(length = 30, nullable = false)
    private String bookName;

    @Column(length = 100, nullable = false)
    private String bookAuthor;

    @Column(length = 20, nullable = false)
    private String bookPublisher;

    @Column(length = 20)
    private String bookDate;

    @Column(length = 50)
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

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetailEntity;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BasketEntity> basketEntityList;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList;

    @OneToMany(mappedBy = "bookEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RecentEntity> recentEntityList;
}
