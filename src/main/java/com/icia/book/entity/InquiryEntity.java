package com.icia.book.entity;

import com.icia.book.dto.InquiryDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="inquiry_table")
@Entity
public class InquiryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String inquiryTitle;

    @Column(length = 20, nullable = false)
    private String inquiryWriter;

    @Column(length = 500, nullable = false)
    private String inquiryContents;

    @Column()
    private int inquiryStatus;

    @CreationTimestamp
    @Column()
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cscenter_category_id")
    private CsCenterCategoryEntity cscenterCategoryEntity;

    @OneToOne(mappedBy = "inquiryEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private InquiryCommentEntity inquiryCommentEntity;

    public static InquiryEntity toDeleteCategory(InquiryEntity inquiryEntity) {
        inquiryEntity.setCscenterCategoryEntity(null);
        return inquiryEntity;
    }

    public static InquiryEntity toSaveEntity(InquiryDTO inquiryDTO, MemberEntity memberEntity, CsCenterCategoryEntity csCenterCategoryEntity) {
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryTitle(inquiryDTO.getInquiryTitle());
        inquiryEntity.setInquiryWriter(inquiryDTO.getInquiryWriter());
        inquiryEntity.setInquiryContents(inquiryDTO.getInquiryContents());
        inquiryEntity.setInquiryStatus(0);
        inquiryEntity.setMemberEntity(memberEntity);
        inquiryEntity.setCscenterCategoryEntity(csCenterCategoryEntity);
        return inquiryEntity;
    }

    public static InquiryEntity cancelStatus(InquiryEntity inquiryEntity) {
        inquiryEntity.setInquiryStatus(2);
        return inquiryEntity;
    }

    public static InquiryEntity statusUpdate(InquiryEntity inquiryEntity, int status) {
        inquiryEntity.setInquiryStatus(status);
        return inquiryEntity;
    }
}
