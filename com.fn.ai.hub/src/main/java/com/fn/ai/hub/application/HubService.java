package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubCreateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.repository.HubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    public HubResponseDto createHub(HubCreateRequestDto requestDto) {
        String name = requestDto.name();
        String address = requestDto.address();
        Double latitude = requestDto.latitude();
        Double longitude = requestDto.longitude();

        hubRepository.findByName(name);

        Hub hub = new Hub(name, address, latitude, longitude);

        return null;
    }
}
