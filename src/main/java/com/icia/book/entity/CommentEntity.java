package com.icia.book.entity;

import com.icia.book.dto.CommentDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter(AccessLevel.PRIVATE)
@Getter
@Entity
@Table(name = "comment_table")
public class CommentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column(length = 300, nullable = false)
    private String commentContents;

    @Column(nullable = false)
    private int commentScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    private BookEntity bookEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    @OneToMany(mappedBy = "commentEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CommentLikeEntity> commentLikeEntityList;

    public static CommentEntity toSaveEntity(CommentDTO commentDTO, MemberEntity memberEntity, BookEntity bookEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDTO.getCommentWriter());
        commentEntity.setCommentContents(commentDTO.getCommentContents());
        commentEntity.setCommentScore(commentDTO.getCommentScore());
        commentEntity.setMemberEntity(memberEntity);
        commentEntity.setBookEntity(bookEntity);
        return commentEntity;
    }
}
