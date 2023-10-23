package com.icia.book.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "letter_table")
@Entity
public class LetterEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 50, nullable = false)
    private String letterTitle;

    @Column(length = 500, nullable = false)
    private String letterContents;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_Id")
    private MemberEntity senderEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_Id")
    private MemberEntity receiverEntity;

}
