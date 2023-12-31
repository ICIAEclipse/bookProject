package com.icia.book.service;

import com.icia.book.dto.CategoryDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CategoryEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final BookRepository bookRepository;
    public List<CategoryDTO> findAll(){
        List<CategoryEntity> categoryEntityList = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = new ArrayList<>();
        categoryEntityList.forEach(categoryEntity -> {
            categoryDTOList.add(CategoryDTO.toCategoryDTO(categoryEntity));
        });
        return categoryDTOList;
    }

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
        CategoryEntity categoryUpdateEntity = CategoryEntity.toCategoryEntity(categoryDTO);
        categoryRepository.save(categoryUpdateEntity);
    }

    @Transactional
    public void delete(Long id) {
        CategoryEntity categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        List<BookEntity> bookEntityList = categoryEntity.getBookEntityList();
        if(categoryRepository.findByCategoryId("000").isEmpty()){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId("000");
            categoryDTO.setCategoryName("미분류");
            CategoryEntity categoryEntity1 = CategoryEntity.toSaveCategoryEntity(categoryDTO);
            categoryRepository.save(categoryEntity1);
        }
        CategoryEntity categoryEntity2 = categoryRepository.findByCategoryId("000").orElseThrow(()-> new NoSuchElementException());
        bookEntityList.forEach(bookEntity -> {
            BookEntity book = BookEntity.toDeleteCategoryBookEntity(bookEntity, categoryEntity2);
            bookRepository.save(book);
        });
        categoryRepository.deleteById(id);
    }
}
