package com.icia.book.service;

import com.icia.book.dto.CategoryDTO;
import com.icia.book.entity.CategoryEntity;
import com.icia.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public Page<CategoryDTO> findAll(int page, String type, String q) {
        page = page - 1;
        int pageLimit = 10;
        Page<CategoryEntity> categoryEntityList = null;
        // 검색인지 구분
        if (q.equals("")) {
            // 일반 페이징
            categoryEntityList = categoryRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.ASC, "id")));
        } else {
            if (type.equals("categoryName")) {
                categoryEntityList = categoryRepository.findByCategoryNameContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.ASC, "categoryId")));
            } else if (type.equals("categoryId")) {
                categoryEntityList = categoryRepository.findByCategoryIdContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.ASC, "categoryId")));
            }
        }
        Page<CategoryDTO> categoryDTOList = categoryEntityList.map(categoryEntity ->
                CategoryDTO.builder()
                        .id(categoryEntity.getId())
                        .categoryId(categoryEntity.getCategoryId())
                        .categoryName(categoryEntity.getCategoryName())
                        .build()
        );
        return categoryDTOList;
    }

    public Long save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.toSaveCategoryEntity(categoryDTO);
        return categoryRepository.save(categoryEntity).getId();
    }

    public void update(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.toCategoryEntity(categoryDTO);
        categoryRepository.save(categoryEntity);
    }

    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }
}
