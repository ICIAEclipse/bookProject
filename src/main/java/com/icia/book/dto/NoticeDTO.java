package com.icia.book.dto;

import com.icia.book.contoroller.util.UtilClass;
import com.icia.book.entity.NoticeEntity;
import com.icia.book.entity.NoticeFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NoticeDTO {

    private Long id;
    private String noticeWriter;
    private String noticeTitle;
    private String noticeContents;
    private int fileAttached;
    private String createdAt;
    private List<MultipartFile> noticeFile;
    private List<NoticeFileDTO> noticeFileList = new ArrayList<>();

    public static NoticeDTO toDTO(NoticeEntity noticeEntity) {
        NoticeDTO noticeDTO = new NoticeDTO();
        noticeDTO.setId(noticeEntity.getId());
        noticeDTO.setNoticeWriter(noticeEntity.getNoticeWriter());
        noticeDTO.setNoticeTitle(noticeEntity.getNoticeTitle());
        noticeDTO.setNoticeContents(noticeEntity.getNoticeContents());
        noticeDTO.setFileAttached(noticeEntity.getFileAttached());
        noticeDTO.setCreatedAt(UtilClass.dateFormat(noticeEntity.getCreatedAt()));
        if(noticeEntity.getFileAttached() == 1){
            for(NoticeFileEntity noticeFileEntity : noticeEntity.getNoticeFileEntityList()){
                noticeDTO.getNoticeFileList().add(NoticeFileDTO.toDTO(noticeFileEntity));
            }
        }
        return noticeDTO;
    }
}
