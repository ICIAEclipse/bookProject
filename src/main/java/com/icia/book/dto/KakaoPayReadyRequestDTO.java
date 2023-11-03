package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayReadyRequestDTO {
    private String cid;
    private String partner_order_id;
    private String partner_user_id;
    private String item_name;
    private int quantity;
    private int total_amount;
    private int tax_free_amount;
    private String approval_url;
    private String cancel_url;
    private String fail_url;

    public static KakaoPayReadyRequestDTO requestMapping(KakaoPayReadyRequestDTO kakaoPayReadyRequestDTO, String partNerUserId) {
        kakaoPayReadyRequestDTO.setCid("TC0ONETIME"); //테스트결제코드
        kakaoPayReadyRequestDTO.setTax_free_amount((int) Math.ceil((double) kakaoPayReadyRequestDTO.getTotal_amount()*10/11));
        kakaoPayReadyRequestDTO.setApproval_url("http://localhost:8090/order/pay/success");
        kakaoPayReadyRequestDTO.setCancel_url("http://localhost:8090/order/pay/cancle");
        kakaoPayReadyRequestDTO.setFail_url("http://localhost:8090/order/pay/fail");
        return kakaoPayReadyRequestDTO;
    }
}
