package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubResponseDto;
import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.exception.HubNotFoundException;

import java.util.List;
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

        return HubResponseDto.of(hub);
    }

    public void initializeHubs() {
        List<HubCreateRequestDto> hubs = List.of(
                new HubCreateRequestDto("서울특별시 센터", "서울특별시 송파구 송파대로 55", 37.51457, 127.10427),
                new HubCreateRequestDto("경기 북부 센터", "경기도 고양시 덕양구 권율대로 570", 37.65849, 126.83272),
                new HubCreateRequestDto("경기 남부 센터", "경기도 이천시 덕평로 257-21", 37.20442, 127.45507),
                new HubCreateRequestDto("부산광역시 센터", "부산 동구 중앙대로 206", 35.13651, 129.05702),
                new HubCreateRequestDto("대구광역시 센터", "대구 북구 태평로 161", 35.88559, 128.58826),
                new HubCreateRequestDto("인천광역시 센터", "인천 남동구 정각로 29", 37.44747, 126.70155),
                new HubCreateRequestDto("광주광역시 센터", "광주 서구 내방로 111", 35.16003, 126.85144),
                new HubCreateRequestDto("대전광역시 센터", "대전 서구 둔산로 100", 36.35267, 127.37657),
                new HubCreateRequestDto("울산광역시 센터", "울산 남구 중앙로 201", 35.53838, 129.31136),
                new HubCreateRequestDto("세종특별자치시 센터", "세종특별자치시 한누리대로 2130", 36.48021, 127.28902),
                new HubCreateRequestDto("강원특별자치도 센터", "강원특별자치도 춘천시 중앙로 1", 37.88539, 127.72979),
                new HubCreateRequestDto("충청북도 센터", "충북 청주시 상당구 상당로 82", 36.63588, 127.49102),
                new HubCreateRequestDto("충청남도 센터", "충남 홍성군 홍북읍 충남대로 21", 36.59793, 126.66059),
                new HubCreateRequestDto("전북특별자치도 센터", "전북특별자치도 전주시 완산구 효자로 225", 35.82422, 127.14810),
                new HubCreateRequestDto("전라남도 센터", "전남 무안군 삼향읍 오룡길 1", 34.81378, 126.38207),
                new HubCreateRequestDto("경상북도 센터", "경북 안동시 풍천면 도청대로 455", 36.57412, 128.50790),
                new HubCreateRequestDto("경상남도 센터", "경남 창원시 의창구 중앙대로 300", 35.23963, 128.69243)
        );

        for (HubCreateRequestDto dto : hubs) {
            if (!hubRepository.existsByName(dto.name())) {
                Hub hub = new Hub(dto.name(), dto.address(), dto.latitude(), dto.longitude());
                hubRepository.save(hub);
            }
        }
    }
}
