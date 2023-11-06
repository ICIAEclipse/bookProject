package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class KakaoPayApproveRequestDTO {
    private String cid;
    private String tid;
    private String partner_order_id;
    private String partner_user_id;
    private String pg_token;
}
