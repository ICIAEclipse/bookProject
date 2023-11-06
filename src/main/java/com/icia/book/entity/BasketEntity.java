package com.icia.book.entity;

import com.icia.book.dto.BasketDTO;
import com.icia.book.dto.BookDTO;
import com.icia.book.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.awt.print.Book;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "basket_table")
@Entity
public class BasketEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;


    public static BasketEntity toSaveEntity(BookEntity bookEntity, MemberEntity memberEntity) {
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setBookEntity(bookEntity);
        basketEntity.setMemberEntity(memberEntity);
        return basketEntity;
    }

        public static BasketEntity toSaveMemberId(Long id) {
        BasketEntity basketEntity = new BasketEntity();
        basketEntity.setId(id);
        return basketEntity;
        }



}
