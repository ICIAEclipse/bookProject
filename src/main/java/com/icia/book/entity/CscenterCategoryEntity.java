package com.icia.book.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="center_category_table")
@Entity
public class CscenterCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, nullable = false)
    private String centerCategoryId;

    @Column(length = 20, nullable = false)
    private String centerName;

    @OneToMany(mappedBy = "cscenterCategoryEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<FaqEntity> faqEntityList;
}
