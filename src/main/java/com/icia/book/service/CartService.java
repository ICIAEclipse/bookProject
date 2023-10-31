package com.icia.book.service;

import com.icia.book.dto.CartDTO;
import com.icia.book.entity.CartEntity;
import com.icia.book.repository.CartRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CartService {
    private final CartRepository cartRepository;

    public void save(CartDTO cartDTO) {
        CartEntity cartEntity = CartEntity.toCartEntity(cartDTO);
        cartRepository.save(cartEntity);
    }

    public List<CartDTO> findAll() {
        List<CartEntity> cartEntityList = cartRepository.findAll();
        List<CartDTO> cartDTOList = new ArrayList<>();
        for(CartEntity cartEntity: cartEntityList){
            cartDTOList.add(CartDTO.toCartDTO(cartEntity));
        }
        return cartDTOList;
    }

    public void delete(Long id) {
        cartRepository.deleteById(id);
    }
}
