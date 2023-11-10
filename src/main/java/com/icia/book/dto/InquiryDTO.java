package com.icia.book.dto;

import com.icia.book.contoroller.util.UtilClass;
import com.icia.book.entity.InquiryEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InquiryDTO {

    private Long id;
    private String inquiryTitle;
    private String inquiryWriter;
    private String inquiryContents;
    private int inquiryStatus;
    private Long cscenterCategoryId;
    private String createdAt;
    private int isComment;
    private InquiryCommentDTO inquiryCommentDTO;
    public static InquiryDTO toDTO(InquiryEntity inquiryEntity) {
        InquiryDTO inquiryDTO = new InquiryDTO();
        inquiryDTO.setId(inquiryEntity.getId());
        inquiryDTO.setInquiryTitle(inquiryEntity.getInquiryTitle());
        inquiryDTO.setInquiryWriter(inquiryEntity.getInquiryWriter());
        inquiryDTO.setInquiryContents(inquiryEntity.getInquiryContents());
        inquiryDTO.setInquiryStatus(inquiryEntity.getInquiryStatus());
        inquiryDTO.setCscenterCategoryId(inquiryEntity.getCscenterCategoryEntity().getId());
        inquiryDTO.setCreatedAt(UtilClass.dateTimeFormat(inquiryEntity.getCreatedAt()));
        if(inquiryEntity.getInquiryCommentEntity() != null) {
            inquiryDTO.setIsComment(1);
            inquiryDTO.setInquiryCommentDTO(InquiryCommentDTO.toDTO(inquiryEntity.getInquiryCommentEntity()));
        } else {
            inquiryDTO.setIsComment(0);
        }
        System.out.println("inquiryDTO = " + inquiryDTO);
        return inquiryDTO;
    }
}
