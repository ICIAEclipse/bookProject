package com.icia.book.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "notice_file_table")
@Entity
public class NoticeFileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String originalFileName;

    @Column(length = 50, nullable = false)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "notice_id")
    private NoticeEntity noticeEntity;

    public static NoticeFileEntity toSaveEntity(NoticeEntity savedEntity, String originalFileName, String storedFileName) {
        NoticeFileEntity noticeFileEntity = new NoticeFileEntity();
        noticeFileEntity.setOriginalFileName(originalFileName);
        noticeFileEntity.setStoredFileName(storedFileName);
        noticeFileEntity.setNoticeEntity(savedEntity);
        return noticeFileEntity;
    }
}
