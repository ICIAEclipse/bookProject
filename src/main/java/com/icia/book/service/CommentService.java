package com.icia.book.service;

import com.icia.book.dto.CommentDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.CommentEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.CommentRepository;
import com.icia.book.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public void save(CommentDTO commentDTO) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(commentDTO.getCommentWriter()).orElseThrow(() -> new NoSuchElementException());
        BookEntity bookEntity = bookRepository.findById(commentDTO.getBookId()).orElseThrow(() -> new NoSuchElementException());
        CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDTO, memberEntity, bookEntity);
        commentRepository.save(commentEntity);
    }

    public List<CommentDTO> findAll(Long bookId) {
        BookEntity bookEntity = bookRepository.findById(bookId).orElseThrow(() -> new NoSuchElementException());
        List<CommentEntity> commentEntityList = commentRepository.findByBookEntityOrderByIdDesc(bookEntity);
        List<CommentDTO> commentDTOList = new ArrayList<>();
        commentEntityList.forEach(commentEntity -> {
            CommentDTO commentDTO = CommentDTO.toDTO(commentEntity);
            commentDTOList.add(commentDTO);
        });
        return commentDTOList;
    }
}
