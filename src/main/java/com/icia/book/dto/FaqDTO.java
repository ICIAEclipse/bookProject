package com.icia.book.dto;

import com.icia.book.entity.CsCenterCategoryEntity;
import com.icia.book.entity.FaqEntity;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FaqDTO {

    private Long id;
    private String faqTitle;
    private String faqContents;
    private int faqHits;
    private Long cscenterCategoryId;

    public static FaqDTO toDTO(FaqEntity faqEntity) {
        FaqDTO faqDTO = new FaqDTO();
        faqDTO.setId(faqEntity.getId());
        faqDTO.setFaqTitle(faqEntity.getFaqTitle());
        faqDTO.setFaqContents(faqEntity.getFaqContents());
        faqDTO.setFaqHits(faqEntity.getFaqHits());
        faqDTO.setCscenterCategoryId(faqEntity.getCscenterCategoryEntity().getId());
        return faqDTO;
    }
}
