package com.icia.book.service;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;


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
}
