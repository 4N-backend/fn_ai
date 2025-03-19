package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubCreateDeliveryRouteRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubRouteResponseDto;
import java.util.Queue;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public interface HubRouteService {

    HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto);

    HubRouteResponseDto updateHubRoute(HubRouteUpdateRequestDto requestDto);

    HubRouteResponseDto deleteHubRoute(UUID route_id);

    HubRouteResponseDto getHubRoute(UUID route_id);

    Page<HubRouteResponseDto> getAllHubRoutes(int page,int size,String sortBy,boolean isAsc);

    Page<HubRouteResponseDto> searchHubRoute(int page, int size, String sortBy, boolean isAsc,
        String keyword);

    /**
     * 허브 배달루트 생성
     */
    Queue<HubRouteResponseDto> createDeliveryRoute(HubCreateDeliveryRouteRequestDto requestDto);

}
