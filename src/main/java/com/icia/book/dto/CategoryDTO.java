package com.icia.book.dto;

import com.icia.book.entity.CategoryEntity;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CategoryDTO {

    private Long id;
    private String categoryId;
    private String categoryName;

    public static CategoryDTO toCategoryDTO(CategoryEntity categoryEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();
        categoryDTO.setId(categoryEntity.getId());
        categoryDTO.setCategoryId(categoryEntity.getCategoryId());
        categoryDTO.setCategoryName(categoryEntity.getCategoryName());
        return categoryDTO;
    }
}
