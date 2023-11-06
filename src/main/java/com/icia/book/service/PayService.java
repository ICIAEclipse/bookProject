package com.icia.book.service;

import com.icia.book.dto.KakaoPayReadyRequestDTO;
import com.icia.book.dto.KakaoPayReadyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Transactional
@Service
public class PayService {
    @Value("${kakao-api-admin-key}")
    private String kakao_api_admin_key;

    public KakaoPayReadyResponseDTO kakaoPayReady(KakaoPayReadyRequestDTO kakaoPayReadyRequestDTO, String partNerUserId) {

        kakaoPayReadyRequestDTO = KakaoPayReadyRequestDTO.requestMapping(kakaoPayReadyRequestDTO, partNerUserId);

        System.out.println(kakao_api_admin_key);
        System.out.println(kakaoPayReadyRequestDTO);

        HttpEntity<KakaoPayReadyRequestDTO> requestEntity = new HttpEntity<>(kakaoPayReadyRequestDTO, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayReadyResponseDTO kakaoPayReadyResponseDTO =
                restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoPayReadyResponseDTO.class);;
        return kakaoPayReadyResponseDTO;
    }
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+kakao_api_admin_key);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }
}
