package com.icia.book.service;

import com.icia.book.contoroller.util.UtilClass;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    public Page<OrderDTO> findAllByMemberEntity(Long memberId, int page) {
        int pageLimit = 5;
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new NoSuchElementException());
        Page<OrderEntity> orderEntityPage = orderRepository.findAllByMemberEntity(memberEntity, PageRequest.of(page-1,pageLimit, Sort.by(Sort.Order.desc("id"))));

        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();
        System.out.println(orderEntityPage.getContent().get(0).getOrderDetailEntityList());

        Page<OrderDTO> orderDTOPage = orderEntityPage.map(orderEntity ->
            OrderDTO.builder()
                .id(orderEntity.getId())
                .orderCode(orderEntity.getOrderCode())
                .orderAddress(orderEntity.getOrderAddress())
                .orderAddressDetail(orderEntity.getOrderAddressDetail())
                .orderPostCode(orderEntity.getOrderPostCode())
                .orderMemberName(orderEntity.getOrderMemberName())
                .orderMemberMobile(orderEntity.getOrderMemberMobile())
                .orderStatus(orderEntity.getOrderStatus())
                .orderDate(UtilClass.dateTimeFormat(orderEntity.getOrderDate()))
                .orderTotal(orderEntity.getOrderTotal())
                .orderDetailDTOList(OrderDetailDTO.toList(orderEntity.getOrderDetailEntityList()))
                .build());
        return orderDTOPage;
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


    public Page<OrderDTO> findAllByStatus(int status, int page) {
        page = page - 1;
        int pageLimit = 10;
        Page<OrderEntity> orderEntities = null;
        if(status == -1){
            orderEntities = orderRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        } else {
            orderEntities = orderRepository.findByOrderStatus(status, PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        }
        Page<OrderDTO> orderDTOPage = orderEntities.map(orderEntity ->
                OrderDTO.builder()
                        .id(orderEntity.getId())
                        .orderCode(orderEntity.getOrderCode())
                        .orderDate(UtilClass.dateTimeFormat(orderEntity.getOrderDate()))
                        .orderStatus(orderEntity.getOrderStatus())
                        .orderMemberName(orderEntity.getOrderMemberName())
                        .orderMemberMobile(orderEntity.getOrderMemberMobile())
                        .build()
        );
        return orderDTOPage;
    }

    public void updateStatus(Long id, int status) {
        OrderEntity orderEntity = orderRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        orderEntity = OrderEntity.updateStatus(orderEntity, status);
        orderRepository.save(orderEntity);
    }
}
