package com.icia.book.dto;

import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CartEntity;
import com.icia.book.entity.MemberEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CartDTO {
    private Long id;
    private Long bookId;
    private Long memberId;
    private String memberEmail;
    private BookDTO bookDTO;
    private int count;
    private boolean isDuplicate;

    public static CartDTO toCartDTO(CartEntity cartEntity){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cartEntity.getId());
        cartDTO.setCount(cartEntity.getCount());
        cartDTO.setDuplicate(cartEntity.isDuplicate());
        cartDTO.setBookDTO(BookDTO.toDTO(cartEntity.getBookEntity()));
        return cartDTO;
    }

    public boolean isDuplicate() {
        return isDuplicate;
    }

    public void setDuplicate(boolean duplicate) {
        isDuplicate = duplicate;
    }
}
