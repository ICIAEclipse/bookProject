package com.icia.book.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
public class AladinSearchDTO {
    private String version;
    private String title;
    private String link;
    private String pubDate;
    private String imageUrl;
    private Integer totalResults;
    private String startIndex;
    private String itemsPerPage;
    private String query;
    private String searchCategoryId;
    private String searchCategoryName;
    private String logo;
    private List<AladinBookDTO> item = new ArrayList<>();
}
