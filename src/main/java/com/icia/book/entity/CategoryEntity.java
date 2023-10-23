package com.icia.book.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Table(name = "category_table")
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, unique = true, nullable = false)
    private String categoryId;

    @Column(length = 20, nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BookEntity> bookEntityList;
}
