package com.icia.book.service;

import com.icia.book.dto.CartDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CartEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.CartRepository;
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
public class CartService {
    private final CartRepository cartRepository;
    private final MemberRepository memberRepository;
    private final BookRepository bookRepository;

    public boolean save(CartDTO cartDTO) {
        MemberEntity memberEntity = memberRepository.findById(cartDTO.getMemberId()).orElseThrow(() -> new NoSuchElementException());
        BookEntity bookEntity = bookRepository.findById(cartDTO.getBookId()).orElseThrow(() -> new NoSuchElementException());
        /*
        BookEntity bookEntity = null;
        if(optionalBookEntity.isEmpty() && optionalMemberEntity.isEmpty()){
            MemberDTO memberDTO = new MemberDTO();
            BookDTO bookDTO = new BookDTO();
            memberDTO.setMemberEmail(cartDTO.getMemberEmail());
            bookDTO.setId(cartDTO.getBookId());
            memberEntity = MemberEntity.toSaveEntity(memberDTO);
            bookEntity = BookEntity.toSaveEntity(bookDTO);
            Long id = bookRepository.save(bookEntity).getId();
            bookEntity = bookRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
            String email = memberRepository.save(memberEntity).getMemberEmail();
            memberEntity = memberRepository.findByMemberEmail(email).orElseThrow(() -> new NoSuchElementException());
        } else {
            bookEntity = optionalBookEntity.get();
            memberEntity = optionalMemberEntity.get();
        }*/
        CartEntity cartEntity = CartEntity.toSaveEntity(cartDTO, memberEntity, bookEntity);

        // 추가코드
        Optional<CartEntity> cartEntityOptional = cartRepository.findByMemberEntityAndBookEntity(memberEntity, bookEntity);
        if (cartEntityOptional.isPresent()) {
            return false;
        } else {
            cartRepository.save(cartEntity);
            return true;
        }
    }

    @Transactional
    public List<CartDTO> findAll(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        List<CartEntity> cartEntityList = memberEntity.getCartEntityList();

//        List<CartEntity> cartEntityList = cartRepository.findAllByMemberEntity(memberEntity);
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(CartEntity cartEntity: cartEntityList){
            cartDTOList.add(CartDTO.toCartDTO(cartEntity));
        }
        return cartDTOList;
    }

    public void delete(Long id) {
        cartRepository.deleteById(id);
    }
}
