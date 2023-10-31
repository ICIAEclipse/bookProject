package com.icia.book.service;

import com.icia.book.dto.OrderDetailDTO;
import com.icia.book.entity.BookEntity;
import com.icia.book.entity.OrderEntity;
import com.icia.book.repository.BookRepository;
import com.icia.book.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final BookRepository bookRepository;

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

            orderDetailDTO.setBookName(bookEntity.getBookName());
            orderDetailDTO.setBookPrice(bookEntity.getBookSalePrice());
            orderDetailDTO.setBookProfile(bookEntity.getBookProfile());
            orderDetailDTO.setBookCount(countList.get(i));

            orderDetailDTOList.add(orderDetailDTO);

        }
        return orderDetailDTOList;
    }
}
