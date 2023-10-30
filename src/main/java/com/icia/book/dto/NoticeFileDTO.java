package com.icia.book.dto;

import com.icia.book.entity.NoticeFileEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class NoticeFileDTO {

    private Long id;
    private String originalFileName;
    private String storedFileName;
    private Long noticeId;

    public static NoticeFileDTO toDTO(NoticeFileEntity noticeFileEntity) {
        NoticeFileDTO noticeFileDTO = new NoticeFileDTO();
        noticeFileDTO.setId(noticeFileEntity.getId());
        noticeFileDTO.setOriginalFileName(noticeFileEntity.getOriginalFileName());
        noticeFileDTO.setStoredFileName(noticeFileEntity.getStoredFileName());
        noticeFileDTO.setNoticeId(noticeFileEntity.getNoticeEntity().getId());
        return noticeFileDTO;
    }
}
