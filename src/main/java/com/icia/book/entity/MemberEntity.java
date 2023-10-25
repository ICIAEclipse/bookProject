package com.icia.book.entity;

import com.icia.book.dto.MemberDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name = "member_table")
@Entity
public class MemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 30, unique = true, nullable = false)
    private String memberEmail;

    @Column(length = 20, nullable = false)
    private String memberPassword;

    @Column(length = 20, nullable = false)
    private String memberName;

    @Column(length = 20, nullable = false)
    private String memberMobile;

    //회원 상태(사용가능, 회원탈퇴, 휴면정지 등)
    // 사용가능 = 0 , 휴면정지 = 1, 회원탈퇴 = 2
    @Column(nullable = false)
    private int memberStatus;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderEntity> orderEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<OrderDetailEntity> orderDetailEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CartEntity> cartEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<BasketEntity> basketEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CommentEntity> commentEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<CommentLikeEntity> commentLikeEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<RecentEntity> recentEntityList;

    @OneToMany(mappedBy = "senderEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<LetterEntity> senderEntityList;

    @OneToMany(mappedBy = "receiverEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<LetterEntity> receiverEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<NoticeEntity> noticeEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<InquiryEntity> inquiryEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<InquiryCommentEntity> inquiryCommentEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<SearchEntity> searchEntityList;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @OrderBy("addressStatus desc, id desc")
    private List<AddressEntity> addressEntityList;

    @OneToOne(mappedBy = "memberEntity", cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private RecentAddressEntity recentAddressEntity;

    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        return memberEntity;
    }

    public static MemberEntity toUpdateEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId());
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setMemberStatus(memberDTO.getMemberStatus());
        return memberEntity;
    }
}
