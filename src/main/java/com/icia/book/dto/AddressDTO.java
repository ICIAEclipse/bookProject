package com.icia.book.dto;

import com.icia.book.entity.AddressEntity;
import lombok.*;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDTO {
    private Long id;
    private String addressName;
    private String postCode;
    private String address;
    private String addressDetail;
    private int addressStatus;
    private Long memberId;

    public static AddressDTO toDTO(AddressEntity addressEntity) {
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setId(addressEntity.getId());
        addressDTO.setAddressName(addressEntity.getAddressName());
        addressDTO.setPostCode(addressEntity.getPostCode());
        addressDTO.setAddress(addressEntity.getAddress());
        addressDTO.setAddressDetail(addressEntity.getAddressDetail());
        addressDTO.setAddressStatus(addressEntity.getAddressStatus());
        addressDTO.setMemberId(addressEntity.getMemberEntity().getId());
        return addressDTO;
    }
}
