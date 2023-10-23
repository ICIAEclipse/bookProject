package com.icia.book.service;

import com.icia.book.dto.CategoryDTO;
import com.icia.book.entity.CategoryEntity;
import com.icia.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryDTO> findAll() {
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryEntityList.forEach(categoryEntity -> {
            categoryDTOList.add(CategoryDTO.toCategoryDTO(categoryEntity));
        });
        return categoryDTOList;
    }

    public Long save(CategoryDTO categoryDTO) {
        CategoryEntity categoryEntity = CategoryEntity.toSaveEntity(categoryDTO);
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
