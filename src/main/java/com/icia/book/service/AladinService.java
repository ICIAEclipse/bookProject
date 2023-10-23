package com.icia.book.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.icia.book.dto.AladinSearchDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class AladinService {

    @Value("${aladin-api-key}")
    private String aladin_api_key;

    private String aladin_search_URL = "http://www.aladin.co.kr/ttb/api/ItemSearch.aspx";
    private String aladin_list_URL = "http://www.aladin.co.kr/ttb/api/ItemList.aspx";
    public AladinSearchDTO allList(int page) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.getForEntity(aladin_list_URL + "?ttbkey="+ aladin_api_key +"&QueryType=ItemNewAll&SearchTarget=book&output=js&Version=20131101", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        AladinSearchDTO aladinSearchDTO = objectMapper.readValue(response.getBody(), AladinSearchDTO.class);
        return aladinSearchDTO;
    }

    public AladinSearchDTO searchList(int page, String type, String q) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        HttpEntity<String> response = restTemplate.getForEntity(aladin_search_URL + "?ttbkey="+ aladin_api_key+ "&Query="+q+"&QueryType="+ type +"&output=js&Version=20131101", String.class);
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        AladinSearchDTO aladinSearchDTO = objectMapper.readValue(response.getBody(), AladinSearchDTO.class);
        return aladinSearchDTO;
    }
}
