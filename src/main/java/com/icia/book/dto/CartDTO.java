package com.icia.book.dto;

import com.icia.book.entity.CartEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class CartDTO {
    private Long id;
    private Long bookId;
    private String memberEmail;
    private String bookCount;
    private int count;

    public static CartDTO toCartDTO(CartEntity cartEntity){
        CartDTO cartDTO = new CartDTO();
        cartDTO.setId(cartEntity.getId());
        cartDTO.setCount(cartEntity.getCount());
        return cartDTO;
    }
}
