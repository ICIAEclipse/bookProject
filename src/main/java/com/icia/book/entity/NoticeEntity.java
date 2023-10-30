package com.icia.book.entity;

import com.icia.book.dto.NoticeDTO;
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

    @Column(length = 50, nullable = false)
    private String noticeTitle;

    @Column(length = 5000, nullable = false)
    private String noticeContents;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Column()
    private int noticeHits;

    @Column()
    private int fileAttached;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "noticeEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<NoticeFileEntity> noticeFileEntityList;

    public static NoticeEntity toSaveEntity(NoticeDTO noticeDTO, MemberEntity memberEntity) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeWriter(noticeDTO.getNoticeWriter());
        noticeEntity.setNoticeContents(noticeDTO.getNoticeContents());
        noticeEntity.setFileAttached(noticeDTO.getFileAttached());
        noticeEntity.setMemberEntity(memberEntity);
        return noticeEntity;
    }

    public static NoticeEntity toUpdateEntity(NoticeDTO noticeDTO, MemberEntity memberEntity) {
        NoticeEntity noticeEntity = new NoticeEntity();
        noticeEntity.setId(noticeDTO.getId());
        noticeEntity.setNoticeTitle(noticeDTO.getNoticeTitle());
        noticeEntity.setNoticeWriter(noticeDTO.getNoticeWriter());
        noticeEntity.setNoticeContents(noticeDTO.getNoticeContents());
        noticeEntity.setFileAttached(noticeDTO.getFileAttached());
        noticeEntity.setMemberEntity(memberEntity);
        return noticeEntity;
    }
}
