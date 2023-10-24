package com.icia.book.entity;

import com.icia.book.dto.CategoryDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "category_table")
@Entity
public class CategoryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 3, unique = true, nullable = false)
    private String categoryId;

    @Column(length = 20, nullable = false)
    private String categoryName;

    @OneToMany(mappedBy = "categoryEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BookEntity> bookEntityList;

    public static CategoryEntity toSaveCategoryEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setCategoryId(categoryDTO.getCategoryId());
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        return categoryEntity;
    }

    public static CategoryEntity toCategoryEntity(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = new CategoryEntity();
        categoryEntity.setId(categoryDTO.getId());
        categoryEntity.setCategoryId(categoryDTO.getCategoryId());
        categoryEntity.setCategoryName(categoryDTO.getCategoryName());
        return categoryEntity;
    }
}
