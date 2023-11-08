package com.icia.book.service;

import com.icia.book.dto.CscenterCategoryDTO;
import com.icia.book.dto.FaqDTO;
import com.icia.book.entity.CsCenterCategoryEntity;
import com.icia.book.entity.FaqEntity;
import com.icia.book.entity.InquiryEntity;
import com.icia.book.repository.CsCenterCategoryRepository;
import com.icia.book.repository.InquiryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CsCenterCategoryService {

    private final CsCenterCategoryRepository csCenterCategoryRepository;
    private final InquiryRepository inquiryRepository;

    public List<CscenterCategoryDTO> findAll() {
        List<CsCenterCategoryEntity> csCenterCategoryEntityList = csCenterCategoryRepository.findAll();
        List<CscenterCategoryDTO> cscenterCategoryDTOList = new ArrayList<>();
        csCenterCategoryEntityList.forEach(csCenterCategoryEntity -> {
            cscenterCategoryDTOList.add(CscenterCategoryDTO.toDTO(csCenterCategoryEntity));
        });
        return cscenterCategoryDTOList;
    }
    public int save(CscenterCategoryDTO cscenterCategoryDTO) {
        if(csCenterCategoryRepository.findByCenterCategoryId(cscenterCategoryDTO.getCenterCategoryId()).isPresent()){
            return 1;
        } else if (csCenterCategoryRepository.findByCenterName(cscenterCategoryDTO.getCenterName()).isPresent()) {
            return 2;
        } else {
            CsCenterCategoryEntity csCenterCategoryEntity = CsCenterCategoryEntity.toSaveEntity(cscenterCategoryDTO);
            csCenterCategoryRepository.save(csCenterCategoryEntity);
            return 0;
        }
    }


    public int update(CscenterCategoryDTO cscenterCategoryDTO) {
        if(csCenterCategoryRepository.findByCenterCategoryId(cscenterCategoryDTO.getCenterCategoryId()).isPresent()){
            return 1;
        } else if (csCenterCategoryRepository.findByCenterName(cscenterCategoryDTO.getCenterName()).isPresent()) {
            return 2;
        } else {
            CsCenterCategoryEntity csCenterCategoryEntity = CsCenterCategoryEntity.toEntity(cscenterCategoryDTO);
            csCenterCategoryRepository.save(csCenterCategoryEntity);
            return 0;
        }
    }

    @Transactional
    public void delete(CscenterCategoryDTO cscenterCategoryDTO) {
        CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(cscenterCategoryDTO.getId()).orElseThrow(() -> new NoSuchElementException());
        csCenterCategoryEntity.getInquiryEntityList().forEach(inquiryEntity -> {
            inquiryEntity = InquiryEntity.toDeleteCategory(inquiryEntity);
            inquiryRepository.save(inquiryEntity);
        });
        csCenterCategoryRepository.deleteById(cscenterCategoryDTO.getId());
    }
}
