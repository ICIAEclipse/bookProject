package com.icia.book.dto;

import com.icia.book.contoroller.util.UtilClass;
import com.icia.book.entity.InquiryCommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class InquiryCommentDTO {

    private Long id;
    private String inquiryCommentWriter;
    private String inquiryCommentContents;
    private String createdAt;
    private Long memberId;
    private Long inquiryId;

    public static InquiryCommentDTO toDTO(InquiryCommentEntity inquiryCommentEntity) {
        InquiryCommentDTO inquiryCommentDTO = new InquiryCommentDTO();
        inquiryCommentDTO.setId(inquiryCommentEntity.getId());
        inquiryCommentDTO.setInquiryCommentWriter(inquiryCommentEntity.getInquiryCommentWriter());
        inquiryCommentDTO.setInquiryCommentContents(inquiryCommentEntity.getInquiryCommentContents());
        inquiryCommentDTO.setCreatedAt(UtilClass.dateTimeFormat(inquiryCommentEntity.getCreatedAt()));
        inquiryCommentDTO.setMemberId(inquiryCommentEntity.getMemberEntity().getId());
        inquiryCommentDTO.setInquiryId(inquiryCommentEntity.getInquiryEntity().getId());
        return inquiryCommentDTO;
    }
}
