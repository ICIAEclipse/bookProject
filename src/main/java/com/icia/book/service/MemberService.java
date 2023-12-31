package com.icia.book.service;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.AddressRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        memberRepository.save(memberEntity);
    }


    public boolean login(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberDTO.getMemberEmail()).orElseThrow(() -> new NoSuchElementException());
        if(memberEntity.getMemberStatus() == 1){
            return false;
        } else if(memberDTO.getMemberPassword().equals(memberEntity.getMemberPassword())){
            return true;
        }else{
            return false;
        }
    }

    public Page<MemberDTO> findAll(int page, String type, String q) {

        page = page - 1;
        int pageLimt = 10;
        Page<MemberEntity> memberEntityList = null;
        if (q.equals("")) {
            memberEntityList = memberRepository.findAll(PageRequest.of(page, pageLimt, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            if(type.equals("memberEmail")){
                memberEntityList = memberRepository.findByMemberEmailContaining(q, PageRequest.of(page, pageLimt, Sort.by(Sort.Direction.DESC, "id")));
            } else {
                memberEntityList = memberRepository.findByMemberNameContaining(q, PageRequest.of(page, pageLimt, Sort.by(Sort.Direction.DESC, "id")));
            }
        }
        Page<MemberDTO> memberDTOList = memberEntityList.map(memberEntity ->
                MemberDTO.builder()
                        .id(memberEntity.getId())
                        .memberEmail(memberEntity.getMemberEmail())
                        .memberName(memberEntity.getMemberName())
                        .memberMobile(memberEntity.getMemberMobile())
                        .memberStatus(memberEntity.getMemberStatus())
                        .build()
        );

        return memberDTOList;
    }


    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        MemberDTO memberDTO = MemberDTO.toDTO(memberEntity);
        return memberDTO;
    }




    public void delete(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDTO findByMemberEmail(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }
    
    public void update(MemberDTO memberDTO) {
        System.out.println("서비스 " + memberDTO);
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public void updateDel(MemberDTO memberDTO) {
        System.out.println("마지막 체크 " + memberDTO);
        MemberEntity memberEntity = MemberEntity.toDeleteEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    ///////////////////////////////////////////////////////

    private final AddressRepository addressRepository;

    @Transactional
    public Page<AddressDTO> findAddressByMemberEmail(String memberEmail, int page){
        int pageLimit = 5;

        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());

//        List<AddressEntity> addressEntityList = memberEntity.getAddressEntityList();

        Page<AddressEntity> addressEntityPage = addressRepository.findAllByMemberEntity(memberEntity, PageRequest.of(page-1,pageLimit, Sort.by(Sort.Order.desc("addressStatus"), Sort.Order.desc("id"))));


        Page<AddressDTO> addressDTOPage = addressEntityPage.map(addressEntity ->
            AddressDTO.builder()
                    .id(addressEntity.getId())
                    .postCode(addressEntity.getPostCode())
                    .addressName(addressEntity.getAddressName())
                    .address(addressEntity.getAddress())
                    .addressDetail(addressEntity.getAddressDetail())
                    .addressStatus(addressEntity.getAddressStatus())
                    .memberId(addressEntity.getMemberEntity().getId())
                    .build());
        return addressDTOPage;
    }

    @Transactional
    public void saveAddress(AddressDTO addressDTO, String memberEmail, boolean defaultAddressChecked) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        System.out.println(memberEntity.getAddressEntityList().size());
        if(memberEntity.getAddressEntityList().size()==0){
            addressDTO.setAddressStatus(1);
        }else if(defaultAddressChecked){
            addressDTO.setAddressStatus(1);
            for(AddressEntity savedAddress : memberEntity.getAddressEntityList()){
                if(savedAddress.getAddressStatus()==1){
                    savedAddress = AddressEntity.changeStatusTo0(savedAddress);
                    addressRepository.save(savedAddress);
                }
            }
        }
        AddressEntity addressEntity = AddressEntity.toSaveEntity(addressDTO, memberEntity);
        addressRepository.save(addressEntity);
    }

    @Transactional
    public void setDefaultAddress(String memberEmail, Long addressId) {
        Optional<AddressEntity> optionalAddressEntity = addressRepository.findById(addressId);
        if(optionalAddressEntity.isPresent()){
            MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
            memberEntity.getAddressEntityList().forEach(addressEntity ->{
                if(addressEntity.getAddressStatus() == 1){
                    addressEntity = AddressEntity.changeStatusTo0(addressEntity);
                    addressRepository.save(addressEntity);
                }
            });
            AddressEntity addressEntity = optionalAddressEntity.get();
            addressEntity = AddressEntity.changeStatusTo1(addressEntity);
            addressRepository.save(addressEntity);
        }
    }

    @Transactional
    public boolean deleteAddress(Long id, String memberEmail) {
        AddressEntity addressEntity = addressRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        if(addressEntity.getMemberEntity().getMemberEmail().equals(memberEmail)){
            addressRepository.deleteById(id);
            return true;
        }else {
            return false;
        }
    }

    public AddressDTO findDefaultAddressByMemberEmail(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        Optional<AddressEntity> optionalAddressEntity = addressRepository.findByMemberEntityAndAddressStatus(memberEntity, 1);
        if(optionalAddressEntity.isPresent()){
            return AddressDTO.toDTO(optionalAddressEntity.get());
        }else {
            return null;
        }
    }

    public void statusUpdate(MemberDTO memberDTO) {
        MemberEntity memberEntity = memberRepository.findById(memberDTO.getId()).orElseThrow(() -> new NoSuchElementException());
        memberEntity = MemberEntity.toStatusUpdate(memberEntity, memberDTO.getMemberStatus());
        memberRepository.save(memberEntity);
    }

    public List<AddressDTO> findAllAddressByMemberEmail(String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        List<AddressEntity> addressEntityList = addressRepository.findAllByMemberEntity(memberEntity);
        List<AddressDTO> addressDTOList = new ArrayList<>();
        addressEntityList.forEach(addressEntity -> {
            addressDTOList.add(AddressDTO.toDTO(addressEntity));
        });
        return addressDTOList;
    }
}
