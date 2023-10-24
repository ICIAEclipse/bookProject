package com.icia.book.entity;

import com.icia.book.dto.AddressDTO;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter(AccessLevel.PRIVATE)
@Table(name="address_table")
@Entity
public class AddressEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 10, nullable = false)
    private String addressName;

    @Column(length = 10, nullable = false)
    private String postCode;

    @Column(length = 50, nullable = false)
    private String address;

    @Column(length = 50)
    private String addressDetail;

    @Column()
    private int addressStatus;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static AddressEntity toSaveEntity(AddressDTO addressDTO, MemberEntity memberEntity) {
        AddressEntity addressEntity = new AddressEntity();
        addressEntity.setAddressName(addressDTO.getAddressName());
        addressEntity.setPostCode(addressDTO.getPostCode());
        addressEntity.setAddress(addressDTO.getAddress());
        addressEntity.setAddressDetail(addressDTO.getAddressDetail());
        addressEntity.setMemberEntity(memberEntity);
        return addressEntity;
    }
}
