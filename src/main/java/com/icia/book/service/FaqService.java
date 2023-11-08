package com.icia.book.service;

import com.icia.book.dto.FaqDTO;
import com.icia.book.entity.CategoryEntity;
import com.icia.book.entity.CsCenterCategoryEntity;
import com.icia.book.entity.FaqEntity;
import com.icia.book.repository.CsCenterCategoryRepository;
import com.icia.book.repository.FaqRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class FaqService {

    private final FaqRepository faqRepository;
    private final CsCenterCategoryRepository csCenterCategoryRepository;

    public void save(FaqDTO faqDTO) {
        CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(faqDTO.getCscenterCategoryId()).orElseThrow(() -> new NoSuchElementException());
        FaqEntity faqEntity = FaqEntity.toSaveEntity(faqDTO, csCenterCategoryEntity);
        faqRepository.save(faqEntity);
    }

    public Page<FaqDTO> findAll(String q, Long categoryId, int page) {
        page = page - 1;
        int pageLimit = 10;
        Page<FaqEntity> faqEntityPage = null;
        if (q.equals("") && categoryId == null) {
            faqEntityPage = faqRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else if (q.equals("") && categoryId != null) {
            CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException());
            faqEntityPage = faqRepository.findByCscenterCategoryEntity(csCenterCategoryEntity, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else if (!q.equals("") && categoryId == null) {
            faqEntityPage = faqRepository.findByFaqTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            System.out.println("질의어 + 카테고리");
            CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(categoryId).orElseThrow(() -> new NoSuchElementException());
            faqEntityPage = faqRepository.findByCscenterCategoryEntityAndFaqTitleContaining(csCenterCategoryEntity, q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        Page<FaqDTO> faqDTOPage = faqEntityPage.map(faqEntity ->
            FaqDTO.builder()
                    .id(faqEntity.getId())
                    .faqTitle(faqEntity.getFaqTitle())
                    .cscenterCategoryId(faqEntity.getCscenterCategoryEntity().getId())
                    .build()
        );
        return faqDTOPage;
    }

    @Transactional
    public FaqDTO findById(Long id) {
        FaqEntity faqEntity = faqRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        FaqDTO faqDTO = FaqDTO.toDTO(faqEntity);
        return faqDTO;
    }

    public void update(FaqDTO faqDTO) {
        CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(faqDTO.getCscenterCategoryId()).orElseThrow(() -> new NoSuchElementException());
        FaqEntity faqEntity = FaqEntity.toUpdateEntity(faqDTO, csCenterCategoryEntity);
        faqRepository.save(faqEntity);
    }

    public void delete(Long id) {
        faqRepository.deleteById(id);
    }

    @Transactional
    public Page<FaqDTO> findAllById(Long id, int page) {
        page = page - 1;
        int pageLimit = 5;
        Page<FaqEntity> faqEntities = null;
        if(id == -1L){
            faqEntities = faqRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "faqHits")));
        } else {
            CsCenterCategoryEntity csCenterCategoryEntity = csCenterCategoryRepository.findById(id).orElseThrow(()->new NoSuchElementException());
            faqEntities = faqRepository.findByCscenterCategoryEntity(csCenterCategoryEntity, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "faqHits")));
        }
        Page<FaqDTO> faqDTOPage = faqEntities.map(faqEntity ->
                FaqDTO.builder()
                        .id(faqEntity.getId())
                        .faqTitle(faqEntity.getFaqTitle())
                        .faqContents(faqEntity.getFaqContents())
                        .faqHits(faqEntity.getFaqHits())
                        .cscenterCategoryId(faqEntity.getCscenterCategoryEntity().getId())
                        .build()
                );
        return faqDTOPage;
    }

    @Transactional
    public void upHits(Long id) {
        faqRepository.upHits(id);
    }
}
