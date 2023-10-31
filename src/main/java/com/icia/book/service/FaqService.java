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
}
