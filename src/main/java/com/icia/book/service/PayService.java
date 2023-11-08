package com.icia.book.service;

import com.icia.book.dto.KakaoPayApproveRequestDTO;
import com.icia.book.dto.KakaoPayApproveResponseDTO;
import com.icia.book.dto.KakaoPayReadyRequestDTO;
import com.icia.book.dto.KakaoPayReadyResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
@Transactional
@Service
public class PayService {
    @Value("${kakao-api-admin-key}")
    private String kakao_api_admin_key;


    private final String cid = "TC0ONETIME";
    private KakaoPayReadyResponseDTO kakaoPayReadyResponseDTO;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;

    public KakaoPayReadyResponseDTO kakaoPayReady(KakaoPayReadyRequestDTO kakaoPayReadyRequestDTO, String partNerUserId) {

        kakaoPayReadyRequestDTO = KakaoPayReadyRequestDTO.requestMapping(kakaoPayReadyRequestDTO, partNerUserId);
        partner_order_id = kakaoPayReadyRequestDTO.getPartner_order_id();
        partner_user_id = kakaoPayReadyRequestDTO.getPartner_user_id();

        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        parameters.add("cid", cid);
        parameters.add("partner_order_id", partner_order_id);
        parameters.add("partner_user_id", partner_user_id);
        parameters.add("item_name", kakaoPayReadyRequestDTO.getItem_name());
        parameters.add("quantity", String.valueOf(kakaoPayReadyRequestDTO.getQuantity()));
        parameters.add("total_amount", String.valueOf(kakaoPayReadyRequestDTO.getTotal_amount()));
        parameters.add("tax_free_amount", String.valueOf((int) Math.ceil((double) kakaoPayReadyRequestDTO.getTotal_amount()*10/11)));
        parameters.add("approval_url", "http://localhost:8090/order/pay/success"); // 성공 시 redirect url
        parameters.add("cancel_url", "http://localhost:8090/order/pay/cancel"); // 취소 시 redirect url
        parameters.add("fail_url", "http://localhost:8090/order/pay/fail"); // 실패 시 redirect url

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        kakaoPayReadyResponseDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/ready",
                requestEntity,
                KakaoPayReadyResponseDTO.class
        );
        tid = kakaoPayReadyResponseDTO.getTid();
        return kakaoPayReadyResponseDTO;
    }
    private HttpHeaders getHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "KakaoAK "+kakao_api_admin_key);
        headers.set("Content-type", "application/x-www-form-urlencoded;charset=utf-8");
        return headers;
    }

    public KakaoPayApproveResponseDTO kakaoPayApprove(KakaoPayApproveRequestDTO kakaoPayApproveRequestDTO, String pgToken) {
        MultiValueMap<String, String> parameters = new LinkedMultiValueMap<>();
        System.out.println(kakaoPayApproveRequestDTO);
        System.out.println("pgToken : " + pgToken);
        parameters.add("cid", cid);
        parameters.add("tid", tid);
        parameters.add("partner_order_id", partner_order_id);
        parameters.add("partner_user_id", partner_user_id);
        parameters.add("pg_token", pgToken);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(parameters, this.getHeaders());
        RestTemplate restTemplate = new RestTemplate();

        KakaoPayApproveResponseDTO kakaoPayApproveResponseDTO = restTemplate.postForObject(
                "https://kapi.kakao.com/v1/payment/approve",
                requestEntity,
                KakaoPayApproveResponseDTO.class
        );
        return kakaoPayApproveResponseDTO;
    }
}
