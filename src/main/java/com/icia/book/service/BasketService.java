package com.icia.book.service;

import com.icia.book.dto.BookDTO;
import com.icia.book.entity.BasketEntity;
import com.icia.book.repository.BasketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;

    public static void save(String isbn, String loginEmail) {
//        BasketRepository.save();
    }
}
