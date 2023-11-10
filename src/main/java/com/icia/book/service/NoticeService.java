package com.icia.book.service;

import com.icia.book.contoroller.util.UtilClass;
import com.icia.book.dto.NoticeDTO;
import com.icia.book.entity.MemberEntity;
import com.icia.book.entity.NoticeEntity;
import com.icia.book.entity.NoticeFileEntity;
import com.icia.book.repository.MemberRepository;
import com.icia.book.repository.NoticeFileRepository;
import com.icia.book.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;
    private final MemberRepository memberRepository;
    private final NoticeFileRepository noticeFileRepository;

    @PersistenceContext
    private EntityManager entityManager;

    public Long save(NoticeDTO noticeDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(noticeDTO.getNoticeWriter()).orElseThrow(() -> new NoSuchElementException());
        if (noticeDTO.getNoticeFile().get(0).isEmpty()) {
            noticeDTO.setFileAttached(0);
        } else {
            noticeDTO.setFileAttached(1);
        }
        NoticeEntity noticeEntity = NoticeEntity.toSaveEntity(noticeDTO, memberEntity);
        NoticeEntity savedEntity = noticeRepository.save(noticeEntity);
        List<MultipartFile> noticeFileList = noticeDTO.getNoticeFile();
        noticeFileList.forEach(multipartFile -> {
            String originalFileName = multipartFile.getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
            String savePath = "D:\\springboot_img\\notice\\" + storedFileName;
            try {
                multipartFile.transferTo((new File(savePath)));
            } catch (IOException e) {
                System.out.println("게시물 파일 저장 오류");
                throw new RuntimeException(e);
            }
            NoticeFileEntity noticeFileEntity = NoticeFileEntity.toSaveEntity(savedEntity, originalFileName, storedFileName);
            noticeFileRepository.save(noticeFileEntity);
        });

        return savedEntity.getId();
    }

    public Page<NoticeDTO> findAll(String q, int page) {
        page = page - 1;
        int pageLimit = 10;
        Page<NoticeEntity> noticeEntities = null;
        if (q.equals("")) {
            noticeEntities = noticeRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            noticeEntities = noticeRepository.findByNoticeTitleContaining(q, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        Page<NoticeDTO> noticeDTOPage = noticeEntities.map(noticeEntity ->
                NoticeDTO.builder()
                        .id(noticeEntity.getId())
                        .noticeTitle(noticeEntity.getNoticeTitle())
                        .noticeWriter(noticeEntity.getNoticeWriter())
                        .createdAt(UtilClass.dateFormat(noticeEntity.getCreatedAt())).build());

        return noticeDTOPage;
    }

    @Transactional
    public NoticeDTO findById(Long id) {
        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        NoticeDTO noticeDTO = NoticeDTO.toDTO(noticeEntity);
        return noticeDTO;
    }

    @Transactional
    public void update(NoticeDTO noticeDTO, List<String> deleteFileList) throws IOException {
        NoticeEntity noticeEntity = noticeRepository.findById(noticeDTO.getId()).orElseThrow(() -> new NoSuchElementException());
        List<NoticeFileEntity> noticeFileEntityList = noticeEntity.getNoticeFileEntityList();
        if(deleteFileList != null){
            if(deleteFileList.size() == noticeFileEntityList.size() && noticeDTO.getNoticeFile().get(0).isEmpty()){
                noticeDTO.setFileAttached(0);
            } else {
                noticeDTO.setFileAttached(1);
            }
            entityManager.clear();
            deleteFileList.forEach(deleteFile -> {
                File file = new File("D:\\springboot_img\\notice\\"+ deleteFile);
                if(file.exists()){
                    file.delete();
                }
                noticeFileRepository.deleteByStoredFileName(deleteFile);
            });
        }
        if(!noticeDTO.getNoticeFile().get(0).isEmpty()){
            noticeDTO.setFileAttached(1);
            List<MultipartFile> noticeFileList = noticeDTO.getNoticeFile();
            for(MultipartFile noticeFile : noticeFileList){
                String originalFileName = noticeFile.getOriginalFilename();
                String storedFileName = System.currentTimeMillis() + "_" + originalFileName;
                String savePath = "D:\\springboot_img\\notice\\";
                noticeFile.transferTo(new File(savePath+storedFileName));
                NoticeFileEntity noticeFileEntity = NoticeFileEntity.toSaveEntity(noticeEntity, originalFileName, storedFileName);
                noticeFileRepository.save(noticeFileEntity);
            }
        }
        MemberEntity memberEntity = memberRepository.findByMemberEmail(noticeDTO.getNoticeWriter()).orElseThrow(() -> new NoSuchElementException());
        NoticeEntity updateEntity = NoticeEntity.toUpdateEntity(noticeDTO, memberEntity);
        noticeRepository.save(updateEntity);
    }

    @Transactional
    public void delete(Long id) {
        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        List<NoticeFileEntity> noticeFileEntityList = noticeEntity.getNoticeFileEntityList();
        noticeFileEntityList.forEach(noticeFileEntity -> {
            File file = new File("D:\\springboot_img\\notice\\"+ noticeFileEntity.getStoredFileName());
            if(file.exists()){
                file.delete();
            }
            noticeFileRepository.delete(noticeFileEntity);
        });
        noticeRepository.deleteById(id);
    }
}
