package com.icia.book.service;

import com.icia.book.dto.OrderDTO;
import com.icia.book.dto.OrderDetailDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.MemberEntity;
import com.icia.book.entity.OrderDetailEntity;
import com.icia.book.entity.OrderEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.MemberRepository;
import com.icia.book.repository.OrderDetailRepository;
import com.icia.book.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final BookRepository bookRepository;
    private final MemberRepository memberRepository;

    public boolean findByOrderCode(String orderCode) {
        Optional<OrderEntity> optionalOrderEntity = orderRepository.findByOrderCode(orderCode);
        if(optionalOrderEntity.isEmpty()){
            return true;
        }else {
            return false;
        }
    }

    public List<OrderDetailDTO> findAllByBookIds(List<Long> bookIdList, List<Integer> countList) {
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        for(int i = 0; i<bookIdList.size(); i++){
            BookEntity bookEntity = bookRepository.findById(bookIdList.get(i)).orElseThrow(() -> new NoSuchElementException());
            OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
            orderDetailDTO.setBookId(bookEntity.getId());
            orderDetailDTO.setBookName(bookEntity.getBookName());
            orderDetailDTO.setBookPrice(bookEntity.getBookSalePrice());
            orderDetailDTO.setBookProfile(bookEntity.getBookProfile());
            orderDetailDTO.setBookCount(countList.get(i));
            orderDetailDTOList.add(orderDetailDTO);
        }
        return orderDetailDTOList;
    }

    @Transactional
    public boolean checkCount(OrderDTO orderDTO) {
        for(OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailDTOList()){
            BookEntity bookEntity = bookRepository.findById(orderDetailDTO.getBookId()).orElseThrow(() -> new NoSuchElementException());
            if(bookEntity.getBookCount()<orderDetailDTO.getBookCount()){
                return false;
            }
        }
        return true;
    }

    @Transactional
    public void saveOrder(OrderDTO orderDTO, String memberEmail) {
        MemberEntity memberEntity = memberRepository.findByMemberEmail(memberEmail).orElseThrow(() -> new NoSuchElementException());
        OrderEntity orderEntity = orderRepository.save(OrderEntity.toSaveEntity(orderDTO,memberEntity));
        for(OrderDetailDTO orderDetailDTO : orderDTO.getOrderDetailDTOList()){
            BookEntity bookEntity = bookRepository.findById(orderDetailDTO.getBookId()).orElseThrow(() -> new NoSuchElementException());
            bookEntity = BookEntity.toCountDownEntity(bookEntity, orderDetailDTO.getBookCount());
            bookRepository.save(bookEntity);
            orderDetailRepository.save(OrderDetailEntity.toSaveEntity(orderDetailDTO, memberEntity, bookEntity, orderEntity));
        }
    }

    @Transactional
    public List<OrderDTO> findAllByMemberEntity(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException());
        List<OrderDTO> orderDTOList = new ArrayList<>();
        memberEntity.getOrderEntityList().forEach(orderEntity -> {
            orderDTOList.add(OrderDTO.toDTO(orderEntity));
        });
        return orderDTOList;
    }

    @Transactional
    public List<OrderDetailDTO> findOrderDetailListById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException());

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        orderEntity.getOrderDetailEntityList().forEach(orderDetailEntity -> {
            orderDetailDTOList.add(OrderDetailDTO.toDTO(orderDetailEntity));
        });

        return orderDetailDTOList;
    }

    @Transactional
    public OrderDTO findById(Long orderId) {
        OrderEntity orderEntity = orderRepository.findById(orderId).orElseThrow(() -> new NoSuchElementException());
        return OrderDTO.toDTO(orderEntity);
    }
}
