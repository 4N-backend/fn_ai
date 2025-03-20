package com.fn.ai.hub.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

@Configuration
public class NaverHttpHeadersConfig {

    private final String CLIENT_ID;
    private final String SECRET_KEY;

    private static final String REQUEST_HEADER_ID = "x-ncp-apigw-api-key-id";
    private static final String REQUEST_HEADER_SECRET = "x-ncp-apigw-api-key";

    // ✅ 생성자로 값을 주입받도록 변경
    public NaverHttpHeadersConfig(
            @Value("${naver.map.client-id}") String clientId,
            @Value("${naver.map.secret-key}") String secretKey
    ) {
        this.CLIENT_ID = clientId;
        this.SECRET_KEY = secretKey;
    }

    @Bean
    public HttpHeaders naverMapHttpHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.set(REQUEST_HEADER_ID, CLIENT_ID);
        headers.set(REQUEST_HEADER_SECRET, SECRET_KEY);
        return headers;
    }
}
