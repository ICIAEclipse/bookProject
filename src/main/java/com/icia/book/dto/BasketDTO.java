package com.icia.book.dto;

import com.icia.book.entity.BasketEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class BasketDTO {
    private Long id;
    private Long bookId;
    private Long memberId;
    private BookDTO bookDTO;

    public static BasketDTO toDTO(BasketEntity basketEntity) {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setId(basketEntity.getId());
        basketDTO.setBookDTO(BookDTO.toDTO(basketEntity.getBookEntity()));
        basketDTO.setMemberId(basketEntity.getMemberEntity().getId());
        return basketDTO;
    }
}
