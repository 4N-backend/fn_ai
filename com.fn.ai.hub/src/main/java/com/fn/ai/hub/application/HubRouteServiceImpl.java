package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.*;
import com.fn.ai.hub.application.dto.response.HubRouteResponseDto;
import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.HubRoute;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.domain.repository.HubRouteRepository;
import com.fn.ai.hub.exception.HubNotFoundException;
import java.util.*;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class HubRouteServiceImpl implements HubRouteService{

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;
    private final MapApiService mapApiService;

    @Override
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        Hub departureHub = hubRepository.findById(departureHubId).orElseThrow(
            HubNotFoundException::new);
        Hub arrivalHub = hubRepository.findById(arrivalHubId)
            .orElseThrow(HubNotFoundException::new);

        String uri = mapApiService.createUri(departureHub.getLocation(), arrivalHub.getLocation());
        long[] responseApi = mapApiService.RequestApi(uri);

        long distance = responseApi[0];
        long travelTime = responseApi[1];

        HubRoute hubRoute = new HubRoute(travelTime,distance,departureHubId,arrivalHubId);
        HubRoute saved = hubRouteRepository.save(hubRoute);
        return HubRouteResponseDto.of(saved);
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
        HubRoute hubRoute = hubRouteRepository.findHubRouteById(route_id).orElseThrow(() -> new IllegalArgumentException("허브루트가 없습니다."));
        return HubRouteResponseDto.of(hubRoute);
    }

    @Override
    public Page<HubRouteResponseDto> getAllHubRoutes(int page, int size, String sortBy,
        boolean isAsc) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<HubRoute> hubRoutePage = hubRouteRepository.findAllByPage(pageable);

        return hubRoutePage.map(HubRouteResponseDto::of);
    }

    @Override
    public Page<HubRouteResponseDto> searchHubRoute(int page, int size, String sortBy,
        boolean isAsc, String keyword) {
        return null;
    }


    @Override
    @Transactional
    public void generateHubRoutes() {
        List<Hub> hubs = hubRepository.findAll();
        Map<UUID, Map<UUID, Long[]>> graph = new HashMap<>();

        // 모든 허브 간 경로 데이터 조회 및 그래프 구성
        for (Hub departureHub : hubs) {
            graph.put(departureHub.getId(), new HashMap<>());
            for (Hub arrivalHub : hubs) {
                if (!departureHub.equals(arrivalHub)) {
                    String uri = mapApiService.createUri(departureHub.getLocation(), arrivalHub.getLocation());
                    long[] responseApi = mapApiService.RequestApi(uri);

                    long distance = responseApi[0];
                    long travelTime = responseApi[1];
                    graph.get(departureHub.getId()).put(arrivalHub.getId(), new Long[]{distance, travelTime});
                }
            }
        }

        // 다익스트라 알고리즘을 실행하여 최단 경로 기반의 HubRoute 생성
        for (Hub startHub : hubs) {
            Map<UUID, Long> distances = new HashMap<>();
            Map<UUID, UUID> previous = new HashMap<>();
            PriorityQueue<ImmutablePair<Long, UUID>> pq = new PriorityQueue<>(Comparator.comparing(ImmutablePair::getLeft));

            distances.put(startHub.getId(), 0L);
            pq.add(new ImmutablePair<>(0L, startHub.getId()));

            while (!pq.isEmpty()) {
                ImmutablePair<Long, UUID> current = pq.poll();
                UUID currentHubId = current.getRight();
                long currentDistance = current.getLeft();

                if (currentDistance > distances.getOrDefault(currentHubId, Long.MAX_VALUE)) continue;

                for (Map.Entry<UUID, Long[]> entry : graph.getOrDefault(currentHubId, new HashMap<>()).entrySet()) {
                    UUID neighborHubId = entry.getKey();
                    long newDist = currentDistance + entry.getValue()[0];

                    if (newDist < distances.getOrDefault(neighborHubId, Long.MAX_VALUE)) {
                        distances.put(neighborHubId, newDist);
                        previous.put(neighborHubId, currentHubId);
                        pq.add(new ImmutablePair<>(newDist, neighborHubId));
                    }
                }
            }

            // 최단 경로 결과를 기반으로 HubRoute 저장
            for (UUID endHubId : previous.keySet()) {
                UUID prevHubId = previous.get(endHubId);
                Long[] routeData = graph.get(prevHubId).get(endHubId);

                if (!hubRouteRepository.existsByDepatureHubIdAndArrivalHubId(prevHubId, endHubId)) {
                    HubRoute hubRoute = new HubRoute(routeData[1], routeData[0], prevHubId, endHubId);
                    hubRouteRepository.save(hubRoute);
                }
            }
        }
    }

    @Override
    public Queue<HubRouteResponseDto> findDeliveryRoute(HubRouteFindRequestDto requestDto) {
        Map<UUID, UUID> previous = new HashMap<>();
        PriorityQueue<ImmutablePair<Long, UUID>> pq = new PriorityQueue<>(Comparator.comparing(ImmutablePair::getLeft));
        Map<UUID, Long> distances = new HashMap<>();
        List<HubRoute> routes = hubRouteRepository.findAll();

        Map<UUID, Map<UUID, Long>> graph = new HashMap<>();
        for (HubRoute route : routes) {
            graph.computeIfAbsent(route.getDepatureHubId(), k -> new HashMap<>())
                    .put(route.getArrivalHubId(), route.getDistance().getValue());
        }

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        distances.put(departureHubId, 0L);
        pq.add(new ImmutablePair<>(0L, departureHubId));

        while (!pq.isEmpty()) {
            ImmutablePair<Long, UUID> current = pq.poll();
            UUID currentHubId = current.getRight();
            long currentDistance = current.getLeft();

            if (currentDistance > distances.getOrDefault(currentHubId, Long.MAX_VALUE)) continue;

            for (Map.Entry<UUID, Long> entry : graph.getOrDefault(currentHubId, new HashMap<>()).entrySet()) {
                UUID neighborHubId = entry.getKey();
                long newDist = currentDistance + entry.getValue();

                if (newDist < distances.getOrDefault(neighborHubId, Long.MAX_VALUE)) {
                    distances.put(neighborHubId, newDist);
                    previous.put(neighborHubId, currentHubId);
                    pq.add(new ImmutablePair<>(newDist, neighborHubId));
                }
            }
        }

        Queue<HubRouteResponseDto> deliveryRoute = new LinkedList<>();
        UUID currentHubId = arrivalHubId;

        while (previous.containsKey(currentHubId)) {
            UUID prevHubId = previous.get(currentHubId);
            hubRouteRepository.findByDepatureHubIdAndArrivalHubId(prevHubId, currentHubId)
                    .map(HubRouteResponseDto::of) // HubRoute -> HubRouteResponseDto 변환
                    .ifPresent(deliveryRoute::add);

            currentHubId = prevHubId;
        }

        return deliveryRoute;
    }

}
