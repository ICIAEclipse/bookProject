package com.icia.book.entity;

import com.icia.book.dto.InquiryCommentDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="inquiry_comment_table")
@Entity
public class InquiryCommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String inquiryCommentWriter;

    @Column(length = 500, nullable = false)
    private String inquiryCommentContents;

    @CreationTimestamp
    @Column()
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private InquiryEntity inquiryEntity;

    public static InquiryCommentEntity toSaveEntity(InquiryCommentDTO inquiryCommentDTO, MemberEntity memberEntity, InquiryEntity inquiryEntity) {
        InquiryCommentEntity inquiryCommentEntity = new InquiryCommentEntity();
        inquiryCommentEntity.setInquiryCommentWriter(inquiryCommentDTO.getInquiryCommentWriter());
        inquiryCommentEntity.setInquiryCommentContents(inquiryCommentDTO.getInquiryCommentContents());
        inquiryCommentEntity.setMemberEntity(memberEntity);
        inquiryCommentEntity.setInquiryEntity(inquiryEntity);
        return inquiryCommentEntity;
    }
}
