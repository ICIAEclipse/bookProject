package com.icia.book.service;

import com.icia.book.dto.BookDTO;
import com.icia.book.entity.BasketEntity;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.BasketRepository;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public void save(String isbn, String loginEmail) {
        System.out.println("테스트중입니다. 값이 잘 들어왔느뇨? " + isbn + loginEmail);

        Optional<BookEntity> bookEntityOptional = bookRepository.findByIsbn(isbn);
        Optional<MemberEntity> memberEntityOptional = memberRepository.findByMemberEmail(loginEmail);
        System.out.println("실행1");
        System.out.println(bookEntityOptional.isPresent());
        System.out.println(memberEntityOptional.isPresent());
        if(bookEntityOptional.isPresent() && memberEntityOptional.isPresent()) {
            System.out.println("실행2");
            BookEntity bookEntity = bookEntityOptional.get();
            MemberEntity memberEntity = memberEntityOptional.get();
            BasketEntity basketEntity = BasketEntity.toSaveEntity(bookEntity, memberEntity);
            basketRepository.save(basketEntity);
        } else {
            throw new NoSuchElementException("없다");
        }

//        BookEntity bookEntity = bookRepository.findByIsbn(isbn).orElseThrow(() -> new NoSuchElementException("에러발생001"));
//        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(() -> new NoSuchElementException("에러발생002"));
//        BasketEntity basketEntity = BasketEntity.toSaveEntity(bookEntity, memberEntity);
//        basketRepository.save(basketEntity);


//        Optional<BookEntity> bookOptional = bookRepository.findByIsbn(isbn);
//        Optional<MemberEntity> memberOptional = memberRepository.findByMemberEmail(loginEmail);
//
//        if (bookOptional.isPresent() && memberOptional.isPresent()) {
//            BookEntity bookEntity = bookOptional.get();
//            MemberEntity memberEntity = memberOptional.get();
//            BasketEntity basketEntity = BasketEntity.toSaveEntity(bookEntity, memberEntity);
//            basketRepository.save(basketEntity);
//        } else {
//            throw new NoSuchElementException("책 또는 회원 못찾음");
//        }
//    }
    }
}
