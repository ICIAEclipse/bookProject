package com.icia.book.service;

import com.icia.book.dto.BookDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CategoryEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final CategoryRepository categoryRepository;
    public void save(BookDTO bookDTO) {
        CategoryEntity categoryEntity = categoryRepository.findById(bookDTO.getCategoryId()).orElseThrow(() -> new NoSuchElementException());
        BookEntity bookEntity = BookEntity.toSaveBookEntity(bookDTO, categoryEntity);
        System.out.println(bookEntity.getBookProfile());
        bookRepository.save(bookEntity);
    }
}
