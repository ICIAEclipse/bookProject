package com.icia.book.service;

import com.icia.book.dto.BookDTO;
import com.icia.book.dto.CategoryDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CategoryEntity;
import com.icia.book.entity.CommentEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.CategoryRepository;
import com.icia.book.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;

    private final CommentRepository commentRepository;

    public boolean save(BookDTO bookDTO) {
        if (!bookRepository.findByIsbn(bookDTO.getIsbn()).isEmpty()) {
            return false;
        }
        Optional<CategoryEntity> optionalCategoryEntity = categoryRepository.findById(bookDTO.getCategoryId());
        CategoryEntity categoryEntity = null;
        if(optionalCategoryEntity.isEmpty()){
            CategoryDTO categoryDTO = new CategoryDTO();
            categoryDTO.setCategoryId("000");
            categoryDTO.setCategoryName("미분류");
            categoryEntity = CategoryEntity.toSaveCategoryEntity(categoryDTO);
            Long id = categoryRepository.save(categoryEntity).getId();
            categoryEntity = categoryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        } else {
            categoryEntity = optionalCategoryEntity.get();
        }
        BookEntity bookEntity = BookEntity.toSaveBookEntity(bookDTO, categoryEntity);
        bookRepository.save(bookEntity);
        return true;
    }

    @Transactional
    public Page<BookDTO> findAll(String q, String categoryId, int page) {
        page = page - 1;
        int pageLimit = 10;
        Page<BookEntity> bookEntityPage = null;
        if (q.equals("") && categoryId.equals("")) {
            bookEntityPage = bookRepository.findAll(PageRequest.of(page, pageLimit));
        } else if (q.equals("") && !categoryId.equals("")) {
            CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId).orElseThrow(() -> new NoSuchElementException());
            bookEntityPage = bookRepository.findByCategoryEntity(categoryEntity, PageRequest.of(page, pageLimit));
        } else if (!q.equals("") && categoryId.equals("")) {
            bookEntityPage = bookRepository.findByBookAuthorContainingOrBookNameContaining(q, q, PageRequest.of(page, pageLimit));
        } else {
            System.out.println("질의어 + 카테고리");
            CategoryEntity categoryEntity = categoryRepository.findByCategoryId(categoryId).orElseThrow(() -> new NoSuchElementException());
            bookEntityPage = bookRepository.findByCategoryEntityAndBookAuthorContainingOrBookNameContaining(categoryEntity, q, q, PageRequest.of(page, pageLimit));
        }
        Page<BookDTO> bookDTOPage = bookEntityPage.map(bookEntity ->
                BookDTO.builder()
                        .id(bookEntity.getId())
                        .isbn(bookEntity.getIsbn())
                        .bookName(bookEntity.getBookName())
                        .bookAuthor(bookEntity.getBookAuthor())
                        .bookPublisher(bookEntity.getBookPublisher())
                        .bookDate(bookEntity.getBookDate())
                        .bookProfile(bookEntity.getBookProfile())
                        .bookCount(bookEntity.getBookCount())
                        .bookStatus(bookEntity.getBookStatus())
                        .bookPrice(bookEntity.getBookPrice())
                        .bookSalePrice(bookEntity.getBookSalePrice())
                        .bookContents(bookEntity.getBookContents())
                        .categoryId(bookEntity.getCategoryEntity().getId())
                        .build()
        );
        return bookDTOPage;
    }

    public List<BookDTO> findAll() {
        List<BookEntity> bookEntityList = bookRepository.findAll();
        List<BookDTO> bookDTOList = new ArrayList<>();
        for (BookEntity bookEntity : bookEntityList) {
            bookDTOList.add(BookDTO.toDTO(bookEntity));
        }
        return bookDTOList;
    }

    public void update(BookDTO bookDTO) {
        BookEntity bookEntity = bookRepository.findByIsbn(bookDTO.getIsbn()).orElseThrow(() -> new NoSuchElementException());
        CategoryEntity categoryEntity = categoryRepository.findById(bookDTO.getCategoryId()).orElseThrow(() -> new NoSuchElementException());
        bookEntity = BookEntity.toUpdateEntity(bookEntity, bookDTO, categoryEntity);
        bookRepository.save(bookEntity);
    }

    public BookDTO findById(Long id) {
        BookEntity bookEntity = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByBookEntityOrderByIdDesc(bookEntity);
        BookDTO bookDTO = BookDTO.toDTO(bookEntity, commentEntityList);
        return bookDTO;
    }
}
