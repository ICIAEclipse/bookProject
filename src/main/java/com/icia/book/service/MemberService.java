package com.icia.book.service;

import com.icia.book.dto.AddressDTO;
import com.icia.book.dto.MemberDTO;
import com.icia.book.entity.AddressEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.AddressRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
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
    public List<AddressDTO> saveAddress(AddressDTO addressDTO, String memberEmail, boolean defaultAddressChecked) {
        // memberEntity를 가져온다.
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        // 기본배송지로 설정시
        if(defaultAddressChecked){
            // addressDTO의 status를 1로 고침
            addressDTO.setAddressStatus(1);
            // 기존 addressEntity를 찾아서
            for(AddressEntity savedAddress : memberEntity.getAddressEntityList()){
                // status가 1인 값을
                if(savedAddress.getAddressStatus()==1){
                    // 0으로 고침
                    savedAddress = AddressEntity.changeStatusTo0(savedAddress);
                    // 고친 addressEntity를 업데이트
                    addressRepository.save(savedAddress);
                }
            }
        }
        // addressDTO를 addressEntity로 고침
        AddressEntity addressEntity = AddressEntity.toSaveEntity(addressDTO, memberEntity);
        // addressDTO를 새로 등록
        addressRepository.save(addressEntity);
//        // addressEntity를 다시 가져오기위해 memberEntity를 갱신
//        memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
//        // addressEntity를 다시 가져옴
        List<AddressEntity> addressEntityList = addressRepository.findAllByMemberEntity(memberEntity, Sort.by(Sort.Direction.DESC, "id"));

        List<AddressDTO> addressDTOList = new ArrayList<>();
        addressEntityList.forEach(savedEntity ->{
            addressDTOList.add(AddressDTO.toDTO(savedEntity));
        });
        return addressDTOList;
    }
  
}
