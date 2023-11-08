package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class KakaoPayReadyResponseDTO {
    private String tid;
    private String next_redirect_app_url;
    private String next_redirect_mod_url;
    private String next_redirect_pc_url;
    private Date created_at;
}
