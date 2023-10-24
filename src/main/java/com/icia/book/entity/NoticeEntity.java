package com.icia.book.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "notice_table")
@Entity
public class NoticeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    @ColumnDefault("'admin'")
    private String noticeWriter;

    @Column(length = 5000, nullable = false)
    private String noticeContents;

    @CreationTimestamp
    @Column()
    private LocalDateTime createdAt;

    @Column()
    private int noticeHits;

    @Column()
    private int fineAttached;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "noticeEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<NoticeFileEntity> noticeFileEntityList;

}
