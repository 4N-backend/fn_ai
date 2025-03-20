package com.fn.ai.hub.application;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fn.ai.hub.domain.vo.Location;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class NaverMapApiServiceImpl implements MapApiService{

    private final HttpHeaders naverMapHttpHeaders;
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private static String REQUEST_URI = "https://naveropenapi.apigw.ntruss.com/map-direction/v1/driving";

    @Override
    public String createUri(Location departureLocation, Location arrivalLocation) {
        String departureLatitude = String.valueOf(departureLocation.getLatitude());
        String departureLongitude = String.valueOf(departureLocation.getLongitude());
        String arrivalLatitude = String.valueOf(arrivalLocation.getLatitude());
        String arrivalLongitude = String.valueOf(arrivalLocation.getLongitude());

        String startLocation = departureLongitude + "," + departureLatitude;
        String goalLocation = arrivalLongitude + "," + arrivalLatitude;

        return UriComponentsBuilder.fromUriString(REQUEST_URI)
                .queryParam("start",startLocation)
                .queryParam("goal",goalLocation)
                .queryParam("option","trafast") // 고속도로 위주 최적화 옵션
                .toUriString();
    }

    @Override
    public long[] RequestApi(String uri) {
        try{
            HttpEntity<String> entity = new HttpEntity<>(naverMapHttpHeaders);
            ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class);

            JsonNode root = objectMapper.readTree(response.getBody());

            JsonNode summary = root.path("route").path("trafast").get(0).path("summary");

            long distanceKm = summary.path("distance").asLong(); // m
            long durationMs = summary.path("duration").asLong();  //ms

            long[] result = new long[2];
            result[0] = distanceKm;
            result[1] = durationMs;

            return result;

        }catch (Exception e){
            throw new RuntimeException("네이버 경로 탐색 API 호출 실패");
        }
    }
}
