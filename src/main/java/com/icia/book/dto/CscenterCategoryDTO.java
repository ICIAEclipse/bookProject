package com.icia.book.dto;

import com.icia.book.entity.CsCenterCategoryEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CscenterCategoryDTO {

    private Long id;
    private String centerCategoryId;
    private String centerName;

    public static CscenterCategoryDTO toDTO(CsCenterCategoryEntity csCenterCategoryEntity) {
        CscenterCategoryDTO cscenterCategoryDTO = new CscenterCategoryDTO();
        cscenterCategoryDTO.setId(csCenterCategoryEntity.getId());
        cscenterCategoryDTO.setCenterCategoryId(csCenterCategoryEntity.getCenterCategoryId());
        cscenterCategoryDTO.setCenterName(csCenterCategoryEntity.getCenterName());
        return cscenterCategoryDTO;
    }
}
