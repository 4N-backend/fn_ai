package com.fn.ai.hub.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class NaverMapConfig {

    @Value("${naver.map.client-id}")
    private static String CLIENT_ID;

    @Value("${naver.map.secret-key}")
    private static String SECRET_KEY;

    private static String REQUEST_HEADER_ID = "x-ncp-apigw-api-key-id";
    private static String REQUEST_HEADER_SECRET = "x-ncp-apigw-api-key";
    private static String REQUEST_URI = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";


    @Bean
    public HttpHeaders naverApiHeaders(){
        HttpHeaders headers = new HttpHeaders();
        headers.set(REQUEST_HEADER_ID, CLIENT_ID);
        headers.set(REQUEST_HEADER_SECRET, SECRET_KEY);
        return headers;
    }

    @Bean
    public String naverBaseUrl(){
        return REQUEST_URI;
    }
}
