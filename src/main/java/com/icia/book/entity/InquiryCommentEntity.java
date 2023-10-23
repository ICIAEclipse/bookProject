package com.icia.book.entity;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "inquiry_id")
    private InquiryEntity inquiryEntity;
}
