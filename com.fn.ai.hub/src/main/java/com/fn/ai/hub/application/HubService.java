package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import java.util.UUID;
import org.springframework.data.domain.Page;

public interface HubService {
    HubResponseDto createHub(HubCreateRequestDto requestDto);
    HubResponseDto getHub(UUID hubId);
    Page<HubResponseDto> getAllHub(int page, int size, String sortBy, boolean isAsc);
    Page<HubResponseDto> searchHub(int page, int size, String sortBy, boolean isAsc, String keyword);
    HubResponseDto updateHub(HubUpdateRequestDto requestDto, UUID hub_id);
    HubResponseDto deleteHub(UUID hubId);
    void initializeHubs();

}
