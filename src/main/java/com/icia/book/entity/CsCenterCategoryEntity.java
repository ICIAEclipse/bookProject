package com.icia.book.entity;

import com.icia.book.dto.CscenterCategoryDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="center_category_table")
@Entity
public class CsCenterCategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, nullable = false)
    private String centerCategoryId;

    @Column(length = 20, nullable = false)
    private String centerName;

    @OneToMany(mappedBy = "cscenterCategoryEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FaqEntity> faqEntityList;

    @OneToMany(mappedBy = "cscenterCategoryEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<InquiryEntity> inquiryEntityList;

    public static CsCenterCategoryEntity toSaveEntity(CscenterCategoryDTO cscenterCategoryDTO) {
        CsCenterCategoryEntity csCenterCategoryEntity = new CsCenterCategoryEntity();
        csCenterCategoryEntity.setCenterCategoryId(cscenterCategoryDTO.getCenterCategoryId());
        csCenterCategoryEntity.setCenterName(cscenterCategoryDTO.getCenterName());
        return csCenterCategoryEntity;
    }

    public static CsCenterCategoryEntity toEntity(CscenterCategoryDTO cscenterCategoryDTO) {
        CsCenterCategoryEntity csCenterCategoryEntity = new CsCenterCategoryEntity();
        csCenterCategoryEntity.setId(cscenterCategoryDTO.getId());
        csCenterCategoryEntity.setCenterCategoryId(cscenterCategoryDTO.getCenterCategoryId());
        csCenterCategoryEntity.setCenterName(cscenterCategoryDTO.getCenterName());
        return csCenterCategoryEntity;
    }
}
