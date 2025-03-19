package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubCreateDeliveryRouteRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import com.fn.ai.hub.application.dto.response.HubRouteResponseDto;
import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.domain.repository.HubRouteRepository;
import com.fn.ai.hub.exception.HubNotFoundException;
import java.util.Queue;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class HubRouteServiceImpl implements HubRouteService{

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;
    private final RestTemplate restTemplate;
    private final HttpHeaders naverApiHeaders;
    private final String naverBaseUrl;

    @Override
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        Hub departureHub = hubRepository.findById(departureHubId).orElseThrow(
            HubNotFoundException::new);
        Hub arrivalHub = hubRepository.findById(arrivalHubId)
            .orElseThrow(HubNotFoundException::new);

        double departureHubLatitude = departureHub.getLocation().getLatitude();
        double departureHubLongitude = departureHub.getLocation().getLongitude();
        double arrivalHubLatitude = arrivalHub.getLocation().getLatitude();
        double arrivalHubLongitude = arrivalHub.getLocation().getLongitude();

        /**
         * TODO naver map api호출
         */


        return null;
    }

    @Override
    public HubRouteResponseDto updateHubRoute(HubRouteUpdateRequestDto requestDto) {
        return null;
    }

    @Override
    public HubRouteResponseDto deleteHubRoute(UUID route_id) {
        return null;
    }

    @Override
    public HubRouteResponseDto getHubRoute(UUID route_id) {
        return null;
    }

    @Override
    public Page<HubRouteResponseDto> getAllHubRoutes(int page, int size, String sortBy,
        boolean isAsc) {
        return null;
    }

    @Override
    public Page<HubRouteResponseDto> searchHubRoute(int page, int size, String sortBy,
        boolean isAsc, String keyword) {
        return null;
    }

    @Override
    public Queue<HubRouteResponseDto> createDeliveryRoute(
        HubCreateDeliveryRouteRequestDto requestDto) {
        return null;
    }
}
