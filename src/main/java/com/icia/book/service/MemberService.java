package com.icia.book.service;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.AddressRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
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
        if(memberDTO.getMemberPassword().equals(memberEntity.getMemberPassword())){
            return true;
        }else{
            return false;
        }
    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity: memberEntityList){
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
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

    ///////////////////////////////////////////////////////

    private final AddressRepository addressRepository;

    @Transactional
    public List<AddressDTO> findAddressByMemberEmail(String memberEmail){
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        List<AddressEntity> addressEntityList = memberEntity.getAddressEntityList();
        List<AddressDTO> addressDTOList = new ArrayList<>();
        for(AddressEntity addressEntity : addressEntityList){
            addressDTOList.add(AddressDTO.toDTO(addressEntity));
        }
        return addressDTOList;
    }

    @Transactional
    public void saveAddress(AddressDTO addressDTO, String memberEmail, boolean defaultAddressChecked) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());

        if(defaultAddressChecked){
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

//        List<AddressEntity> addressEntityList = addressRepository.findAllByMemberEntity(memberEntity, Sort.by(Sort.Direction.DESC, "id"));
//        List<AddressDTO> addressDTOList = new ArrayList<>();
//        addressEntityList.forEach(savedEntity ->{
//            addressDTOList.add(AddressDTO.toDTO(savedEntity));
//        });
//        return addressDTOList;
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
}
