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
    private String nextRedirectAppUrl;
    private String nextRedirectModUrl;
    private String nextRedirectPcUrl;
    private Date createdAt;
}
