package com.icia.book.dto;

import com.icia.book.entity.CsCenterCategoryEntity;
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

}
