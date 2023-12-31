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

import java.util.ArrayList;
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
        System.out.println("확인0" +  isbn + loginEmail);
        BookEntity bookEntity = bookRepository.findByIsbn(isbn).orElseThrow(() -> new NoSuchElementException()); // book isbb 확인
        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(() -> new NoSuchElementException()); // memberEmail 확인

        System.out.println("확인1" + BookDTO.toDTO(bookEntity));
        System.out.println("확인2" + MemberDTO.toDTO(memberEntity));

        Optional<BasketEntity> basketDbEntityOptional = basketRepository.findByMemberEntityAndBookEntity(memberEntity, bookEntity); // 위으 isbn값과 일치하는 id를 bookDbId에 넣음
        System.out.println("실행1");

//        if(basketDbEntityOptional.isPresent()) { // DB에 이미 있으면
//            // 삭제
//            BasketEntity basketEntity = basketDbEntityOptional.get(); // basket엔티티로 만듦
//            basketRepository.delete(basketEntity);
//            return false;
//        }else{
            // 저장
            BasketEntity basketEntity = BasketEntity.toSaveEntity(bookEntity, memberEntity); // basket엔티티로 만듦

            Optional<BasketEntity> basketEntityOptional =basketRepository.findByMemberEntityAndBookEntity(memberEntity, bookEntity);
            if(basketEntityOptional.isPresent()) {
                return false;
            } else {
                basketRepository.save(basketEntity);
                return true;
            }

//        }
    }

    @Transactional
    public List<BasketDTO> findAll(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        System.out.println("이건 확인해보고 싶어서 " + MemberDTO.toDTO(memberEntity));
        List<BasketEntity> basketEntityList = basketRepository.findByMemberEntity(memberEntity);
        List<BasketDTO> basketDTOList = new ArrayList<>();
        basketEntityList.forEach(basketEntity -> {
            basketDTOList.add(BasketDTO.toDTO(basketEntity));
        });
        return basketDTOList;

    }
}

