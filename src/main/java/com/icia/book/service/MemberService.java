package com.icia.book.service;

import com.icia.book.Entity.MemberEntity;
import com.icia.book.dto.MemberDTO;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity: memberEntityList){
            memberEntityList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }
}
