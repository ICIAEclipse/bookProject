package com.icia.book.Entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="faq_table")
@Entity
public class FaqEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String faqTitle;

    @Column(length = 500, nullable = false)
    private String faqContents;

    @Column()
    private int faqHits;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cscenter_category_id")
    private CscenterCategoryEntity cscenterCategoryEntity;

}
