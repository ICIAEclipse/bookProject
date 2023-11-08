package com.icia.book.entity;

import com.icia.book.dto.CartDTO;
import lombok.AccessLevel;
import lombok.Cleanup;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "cart_table")
@Entity
public class CartEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @ColumnDefault("1")
    private int count;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static CartEntity toSaveEntity(MemberEntity memberEntity, BookEntity bookEntity){
        CartEntity cartEntity = new CartEntity();
        cartEntity.setCount(1);
        cartEntity.setBookEntity(bookEntity);
        cartEntity.setMemberEntity(memberEntity);
        return cartEntity;
    }
}
