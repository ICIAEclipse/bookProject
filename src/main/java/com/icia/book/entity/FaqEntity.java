package com.icia.book.entity;

import com.icia.book.dto.FaqDTO;
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
    private CsCenterCategoryEntity cscenterCategoryEntity;

    public static FaqEntity toSaveEntity(FaqDTO faqDTO, CsCenterCategoryEntity csCenterCategoryEntity) {
        FaqEntity faqEntity = new FaqEntity();
        faqEntity.setFaqTitle(faqDTO.getFaqTitle());
        faqEntity.setFaqContents(faqDTO.getFaqContents());
        faqEntity.setFaqHits(0);
        faqEntity.setCscenterCategoryEntity(csCenterCategoryEntity);
        return faqEntity;
    }

    public static FaqEntity toUpdateEntity(FaqDTO faqDTO, CsCenterCategoryEntity csCenterCategoryEntity) {
        FaqEntity faqEntity = new FaqEntity();
        faqEntity.setId(faqDTO.getId());
        faqEntity.setFaqTitle(faqDTO.getFaqTitle());
        faqEntity.setFaqContents(faqDTO.getFaqContents());
        faqEntity.setFaqHits(faqDTO.getFaqHits());
        faqEntity.setCscenterCategoryEntity(csCenterCategoryEntity);
        return faqEntity;
    }
}
