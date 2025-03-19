package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.exception.HubNotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubService {

    private final HubRepository hubRepository;

    public HubResponseDto createHub(HubCreateRequestDto requestDto) {
        String name = requestDto.name();
        String address = requestDto.address();
        Double latitude = requestDto.latitude();
        Double longitude = requestDto.longitude();

        if (hubRepository.findByName(name).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 허브입니다.");
        }

        Hub hub = new Hub(name, address, latitude, longitude);

        Hub saved = hubRepository.save(hub);

        return HubResponseDto.of(saved);
    }


    public HubResponseDto getHub(UUID hubId) {

        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(HubNotFoundException::new);

        return HubResponseDto.of(hub);
    }

    public Page<HubResponseDto> getAllHub(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Hub> hubPage = hubRepository.findAllHub(pageable);

        return hubPage.map(HubResponseDto::of);
    }

    public Page<HubResponseDto> searchHub(int page, int size, String sortBy, boolean isAsc, String keyword) {
        Sort.Direction direction = isAsc ? Direction.ASC : Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<Hub> hubPage = hubRepository.serachHub(keyword,pageable);

        return hubPage.map(HubResponseDto::of);
    }

    @Transactional
    public HubResponseDto updateHub(HubUpdateRequestDto requestDto, UUID hub_id) {

        Hub hub = hubRepository.findById(hub_id)
            .orElseThrow(HubNotFoundException::new);

        hub.updateHub(requestDto.name(), requestDto.address(), requestDto.latitude(),
            requestDto.longitude());

        Hub saved = hubRepository.save(hub);

        return HubResponseDto.of(saved);
    }

    public HubResponseDto deleteHub(UUID hubId) {
        Hub hub = hubRepository.findById(hubId)
            .orElseThrow(HubNotFoundException::new);

        hub.deleteHub();

        return HubResponseDto.of(hub);
    }
}
