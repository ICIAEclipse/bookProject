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
    private String isbn;
    private String memberEmail;
    private String bookId;
    private String memberId;



    public static BasketDTO toSaveDTO(BasketEntity basketEntity) {
        BasketDTO basketDTO = new BasketDTO();
        basketDTO.setIsbn(basketEntity.getBookEntity().getIsbn());
        basketDTO.setMemberId(basketEntity.getMemberEntity().getMemberEmail());
        basketDTO.setId(basketEntity.getId());
        return basketDTO;
    }
}
