package com.icia.book.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class AladinSearchDTO {
    private String version;
    private String title;
    private String link;
    private String pubDate;
    private String imageUrl;
    private Integer totalResults;
    private Integer startIndex;
    private Integer itemsPerPage;
    private String query;
    private String searchCategoryId;
    private String searchCategoryName;
    private String logo;
    private List<AladinBookDTO> item = new ArrayList<>();
}
