package com.icia.book.service;

import com.icia.book.dto.BasketDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.entity.BasketEntity;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.BasketRepository;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BasketService {
    private final BasketRepository basketRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public boolean save(String isbn, String loginEmail) {
        BookEntity bookEntity = bookRepository.findByIsbn(isbn).orElseThrow(() -> new NoSuchElementException()); // book isbb 확인
        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(() -> new NoSuchElementException()); // memberEmail 확인

        System.out.println(BookDTO.toDTO(bookEntity));
        System.out.println(MemberDTO.toDTO(memberEntity));

        Optional<BasketEntity> basketDbEntityOptional = basketRepository.findByMemberEntityAndBookEntity(memberEntity, bookEntity); // 위으 isbn값과 일치하는 id를 bookDbId에 넣음
        System.out.println("실행1");

        if(basketDbEntityOptional.isPresent()) { // DB에 이미 있으면
            // 삭제
            BasketEntity basketEntity = basketDbEntityOptional.get(); // basket엔티티로 만듦
            basketRepository.delete(basketEntity);
            return false;
        }else{
            // 저장
            BasketEntity basketEntity = BasketEntity.toSaveEntity(bookEntity, memberEntity); // basket엔티티로 만듦
            basketRepository.save(basketEntity);
            return true;
        }
    }

    public BasketDTO findAll(MemberDTO memberDTO) {
        String memberEmail = memberDTO.getMemberEmail();
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        System.out.println("무슨 값이 들어있을까? " + MemberDTO.toDTO(memberEntity));
        MemberDTO memberDTO1 = MemberDTO.toDTO(memberEntity);
        Long id = memberDTO1.getId();
        System.out.println("무슨 값이 들어있을까? " + memberDTO1);
        Optional<BasketEntity> basketEntityOptional = basketRepository.findById(id);
        System.out.println("무슨 값일까? " + basketEntityOptional);
        if(basketEntityOptional.isPresent()) {
            BasketEntity basketEntity = basketEntityOptional.get();
            BasketDTO basketDTO = BasketDTO.toSaveDTO(basketEntity);
            return basketDTO;

        } else {
            return null;
        }


//        basketRepository.findByMemberId(memberId);
//      MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(() -> new NoSuchElementException()); // memberEmail 확인





    }
}

