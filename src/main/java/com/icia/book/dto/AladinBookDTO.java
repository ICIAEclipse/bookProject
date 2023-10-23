package com.icia.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AladinBookDTO {
    private String title;
    private String author;
    private String pubDate;
    private String description;
    private String isbn;
    private int priceStandard;
    private String link;
    private String itemId;
    private int priceSales;
    private String categoryId;
    private String creator;
    private String isbn13;
    private String stockStatus;
    private String mileage;
    private String cover;
    private String categoryName;
    private String publisher;
    private int customerReviewRank;
    private String mallType;
    private int salesPoint;
    private boolean adult;
    private boolean fixedPrice;

}
