package com.icia.book.service;

import com.icia.book.contoroller.util.UtilClass;
import com.icia.book.dto.InquiryCommentDTO;
import com.icia.book.dto.InquiryDTO;
import com.icia.book.entity.CsCenterCategoryEntity;
import com.icia.book.entity.InquiryCommentEntity;
import com.icia.book.entity.InquiryEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.CsCenterCategoryRepository;
import com.icia.book.repository.InquiryCommentRepository;
import com.icia.book.repository.InquiryRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    private final CsCenterCategoryRepository csCenterCategoryRepository;
    private final MemberRepository memberRepository;
    private final InquiryCommentRepository inquiryCommentRepository;

    public void save(InquiryDTO inquiryDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(inquiryDTO.getInquiryWriter()).orElseThrow(() -> new NoSuchElementException());
        CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(inquiryDTO.getCscenterCategoryId()).orElseThrow(() -> new NoSuchElementException());
        InquiryEntity inquiryEntity = InquiryEntity.toSaveEntity(inquiryDTO, memberEntity, csCenterCategoryEntity);
        inquiryRepository.save(inquiryEntity);
    }

    public Page<InquiryDTO> memberInquiryList(String loginMember, int page) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginMember).orElseThrow(() -> new NoSuchElementException());
        page = page - 1;
        int pageLimit = 5;
        Page<InquiryEntity> inquiryEntityPage = inquiryRepository.findByMemberEntity(memberEntity, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        Page<InquiryDTO> inquiryDTOPage = inquiryEntityPage.map(inquiryEntity ->
                InquiryDTO.builder()
                        .id(inquiryEntity.getId())
                        .inquiryTitle(inquiryEntity.getInquiryTitle())
                        .createdAt(UtilClass.dateFormat(inquiryEntity.getCreatedAt()))
                        .inquiryStatus(inquiryEntity.getInquiryStatus()).build());

        return inquiryDTOPage;
    }

    public void cancel(Long id) {
        InquiryEntity inquiryEntity = inquiryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        inquiryEntity = InquiryEntity.cancelStatus(inquiryEntity);
        inquiryRepository.save(inquiryEntity);
    }

    @Transactional
    public InquiryDTO findById(Long id) {
        InquiryEntity inquiryEntity = inquiryRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        InquiryDTO inquiryDTO = InquiryDTO.toDTO(inquiryEntity);
        return inquiryDTO;
    }

    public Page<InquiryDTO> findAll(int page, int status) {
        page = page - 1;
        int pageLimit = 5;
        Page<InquiryEntity> inquiryEntityPage = null;
        if(status == -1) {
            inquiryEntityPage = inquiryRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            inquiryEntityPage = inquiryRepository.findByInquiryStatus(status, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        Page<InquiryDTO> inquiryDTOPage = inquiryEntityPage.map(inquiryEntity ->
                InquiryDTO.builder()
                        .id(inquiryEntity.getId())
                        .inquiryTitle(inquiryEntity.getInquiryTitle())
                        .createdAt(UtilClass.dateFormat(inquiryEntity.getCreatedAt()))
                        .inquiryStatus(inquiryEntity.getInquiryStatus()).build());
        return inquiryDTOPage;
    }

    public void commentSave(InquiryCommentDTO inquiryCommentDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(inquiryCommentDTO.getInquiryCommentWriter()).orElseThrow(() -> new NoSuchElementException());
        InquiryEntity inquiryEntity = inquiryRepository.findById(inquiryCommentDTO.getInquiryId()).orElseThrow(() -> new NoSuchElementException());
        InquiryCommentEntity inquiryCommentEntity = InquiryCommentEntity.toSaveEntity(inquiryCommentDTO, memberEntity, inquiryEntity);
        inquiryCommentRepository.save(inquiryCommentEntity);
        inquiryEntity = InquiryEntity.statusUpdate(inquiryEntity, 1);
        inquiryRepository.save(inquiryEntity);
    }
}
