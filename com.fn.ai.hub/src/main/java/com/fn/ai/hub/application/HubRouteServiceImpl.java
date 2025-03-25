package com.fn.ai.hub.application;

import com.fn.ai.hub.application.dto.request.HubRouteCreateRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteFindRequestDto;
import com.fn.ai.hub.application.dto.request.HubRouteUpdateRequestDto;
import com.fn.ai.hub.application.dto.response.HubRouteResponseDto;
import com.fn.ai.hub.domain.CalcHubRouteDistance;
import com.fn.ai.hub.domain.Hub;
import com.fn.ai.hub.domain.HubRoute;
import com.fn.ai.hub.domain.repository.CalcHubRouteRepository;
import com.fn.ai.hub.domain.repository.HubRepository;
import com.fn.ai.hub.domain.repository.HubRouteRepository;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class HubRouteServiceImpl implements HubRouteService {

    private final HubRouteRepository hubRouteRepository;
    private final HubRepository hubRepository;
    private final MapApiService mapApiService;
    private final CalcHubRouteRepository calcHubRouteRepository;
    private final long MAX_DISTANCE_M = 100000;

    @Override
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        Hub departureHub = hubRepository.findById(departureHubId).orElseThrow(
            () -> new IllegalArgumentException("허브가 존재하지 않습니다."));
        Hub arrivalHub = hubRepository.findById(arrivalHubId)
            .orElseThrow(() -> new IllegalArgumentException("허브가 존재하지 않습니다."));

        if (hubRouteRepository.findByDepatureHubIdAndArrivalHubId(departureHubId, arrivalHubId)
            .isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 루트입니다.");
        }

        String uri = mapApiService.createUri(departureHub.getLocation(), arrivalHub.getLocation());
        long[] responseApi = mapApiService.RequestApi(uri);

        long distance = responseApi[0];
        long travelTime = responseApi[1];

        HubRoute hubRoute = new HubRoute(travelTime, distance, departureHubId,
            departureHub.getName().getValue(), arrivalHubId, arrivalHub.getName().getValue());
        HubRoute saved = hubRouteRepository.save(hubRoute);
        return HubRouteResponseDto.of(saved);
    }

    @Override
    @Transactional
    public HubRouteResponseDto updateHubRoute(UUID route_id, HubRouteUpdateRequestDto requestDto) {

        HubRoute hubRoute = hubRouteRepository.findById(route_id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브 루트입니다."));

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        Hub departureHub = hubRepository.findById(departureHubId).orElseThrow(
            () -> new IllegalArgumentException("허브가 존재하지 않습니다."));
        Hub arrivalHub = hubRepository.findById(arrivalHubId)
            .orElseThrow(()-> new IllegalArgumentException("허브가 존재하지 않습니다."));

        if (hubRouteRepository.findByDepatureHubIdAndArrivalHubId(departureHubId, arrivalHubId)
            .isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 루트입니다.");
        }

        String uri = mapApiService.createUri(departureHub.getLocation(), arrivalHub.getLocation());
        long[] responseApi = mapApiService.RequestApi(uri);

        long distance = responseApi[0];
        long travelTime = responseApi[1];

        hubRoute.update(travelTime, distance, departureHubId, departureHub.getName().getValue(),
            arrivalHubId, arrivalHub.getName().getValue());
        HubRoute saved = hubRouteRepository.save(hubRoute);
        return HubRouteResponseDto.of(saved);
    }

    @Override
    public HubRouteResponseDto deleteHubRoute(UUID route_id) {
        HubRoute hubRoute = hubRouteRepository.findById(route_id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 허브루트 입니다."));

        hubRoute.delete();

        return HubRouteResponseDto.of(hubRoute);
    }

    @Override
    public HubRouteResponseDto getHubRoute(UUID route_id) {
        HubRoute hubRoute = hubRouteRepository.findHubRouteById(route_id)
            .orElseThrow(() -> new IllegalArgumentException("허브루트가 없습니다."));
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
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<HubRoute> hubRoutePage = hubRouteRepository.searchHubRoute(pageable, keyword);

        return hubRoutePage.map(HubRouteResponseDto::of);
    }


    /**
     * 미리 모든 허브 끼리의 소요시간과 거리 계산
     */
    @Override
    @Transactional
    public void calculateAllHubRoutes() {
        List<Hub> allHub = hubRepository.findAll();
        ExecutorService executor = Executors.newFixedThreadPool(10);

        List<CalcHubRouteDistance> resultList = Collections.synchronizedList(new ArrayList<>());
        List<CompletableFuture<Void>> futures = new ArrayList<>();

        for (Hub startHub : allHub) {
            for (Hub endHub : allHub) {
                if (!startHub.equals(endHub)) {
                    CompletableFuture<Void> future = CompletableFuture.runAsync(() -> {
                        try {
                            String uri = mapApiService.createUri(startHub.getLocation(), endHub.getLocation());
                            long[] responseApi = mapApiService.RequestApi(uri);

                            long distance = responseApi[0];
                            long travelTime = responseApi[1];

                            CalcHubRouteDistance calcHubDistance = new CalcHubRouteDistance(
                                startHub.getId(), endHub.getId(), travelTime, distance
                            );

                            resultList.add(calcHubDistance);

                        } catch (Exception e) {
                            System.err.println("프로세스 에러" + e.getMessage());
                        }
                    }, executor);

                    futures.add(future);
                }
            }
        }

        // 모든 작업 완료 대기
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // 결과를 일괄 저장
        calcHubRouteRepository.saveAll(resultList);

        executor.shutdown();
    }

    @Override
    public Queue<HubRouteResponseDto> findDeliveryRoute(HubRouteFindRequestDto requestDto) {
        Map<UUID, UUID> previous = new HashMap<>();
        PriorityQueue<ImmutablePair<Long, UUID>> pq = new PriorityQueue<>(
            Comparator.comparing(ImmutablePair::getLeft));
        Map<UUID, Long> distances = new HashMap<>();

        // 저장된 `HubRoute` 데이터 조회
        List<HubRoute> routes = hubRouteRepository.findAll();

        // 그래프 생성 (인접 리스트 방식)
        Map<UUID, Map<UUID, Long>> graph = new HashMap<>();
        for (HubRoute route : routes) {
            graph.computeIfAbsent(route.getDepartureHubId(), k -> new HashMap<>())
                .put(route.getArrivalHubId(), route.getDistance().getValue());
        }

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        // 그래프 상태 출력
        log.info("그래프 데이터 확인: " + graph);
        log.info("출발지: " + departureHubId + ", 도착지: " + arrivalHubId);

        // 초기 다익스트라 알고리즘 설정
        distances.put(departureHubId, 0L);
        pq.add(new ImmutablePair<>(0L, departureHubId));

        while (!pq.isEmpty()) {
            ImmutablePair<Long, UUID> current = pq.poll();
            UUID currentHubId = current.getRight();
            long currentDistance = current.getLeft();

            log.info("탐색 중: 현재 허브 = " + currentHubId + ", 거리 = " + currentDistance);

            // 이미 더 짧은 경로가 발견된 경우 스킵
            if (currentDistance > distances.getOrDefault(currentHubId, Long.MAX_VALUE)) {
                continue;
            }

            // 인접 허브 탐색
            for (Map.Entry<UUID, Long> entry : graph.getOrDefault(currentHubId, new HashMap<>())
                .entrySet()) {
                UUID neighborHubId = entry.getKey();
                long newDist = currentDistance + entry.getValue();

                if (newDist < distances.getOrDefault(neighborHubId, Long.MAX_VALUE)) {
                    distances.put(neighborHubId, newDist);
                    previous.put(neighborHubId, currentHubId); // 이전 허브 저장 (경로 추적용)
                    pq.add(new ImmutablePair<>(newDist, neighborHubId));
                }
            }
        }

        // 최단 경로 추적 (출발지 → 경유지 → 도착지)
        Stack<HubRouteResponseDto> routeStack = new Stack<>();
        UUID currentHubId = arrivalHubId;

        while (previous.containsKey(currentHubId)) {
            UUID prevHubId = previous.get(currentHubId);

            log.info("경로 추적: " + prevHubId + " -> " + currentHubId);

            hubRouteRepository.findByDepatureHubIdAndArrivalHubId(prevHubId, currentHubId)
                .map(HubRouteResponseDto::of)
                .ifPresent(routeStack::push);

            currentHubId = prevHubId;
        }

        // 최단 경로를 `Queue`로 변환
        Queue<HubRouteResponseDto> deliveryRoute = new LinkedList<>();
        while (!routeStack.isEmpty()) {
            deliveryRoute.add(routeStack.pop());
        }

        // 최단 경로가 존재하는 경우 출력
        if (deliveryRoute.isEmpty()) {
            log.info("최단 경로를 찾을 수 없습니다.");
        } else {
            log.info("최단 경로 찾기 완료, 경로:");
            for (HubRouteResponseDto route : deliveryRoute) {
                log.info("🔹 " + route.departureHubId() + " -> " + route.arrivalHubId() + " (거리: "
                    + route.distance() + "m)");
            }
        }
        return deliveryRoute;
    }

    @Override
    @Transactional
    public void generateHubRoutes() {

        List<Hub> hubs = hubRepository.findAll();
        List<UUID> mainHubIds = getMainHubIds();
        List<CalcHubRouteDistance> allDistances = calcHubRouteRepository.findAll();

        Map<UUID, Map<UUID, Long[]>> graph = new HashMap<>();

        connectMainHubs(graph, mainHubIds, allDistances);
        connectGeneralHubs(graph, hubs, mainHubIds, allDistances, MAX_DISTANCE_M);
        connectNearbyMainHubs(graph, hubs, mainHubIds, allDistances, MAX_DISTANCE_M);
        saveHubRoutes(graph);
    }

    private List<UUID> getMainHubIds() {
        return List.of(
            hubRepository.findByName("서울특별시 센터").orElseThrow().getId(),
            hubRepository.findByName("대전광역시 센터").orElseThrow().getId(),
            hubRepository.findByName("대구광역시 센터").orElseThrow().getId()
        );
    }

    private void connectMainHubs(Map<UUID, Map<UUID, Long[]>> graph, List<UUID> mainHubs, List<CalcHubRouteDistance> distances) {
        List<UUID[]> pairs = List.of(
            new UUID[]{mainHubs.get(0), mainHubs.get(1)},
            new UUID[]{mainHubs.get(1), mainHubs.get(2)},
            new UUID[]{mainHubs.get(0), mainHubs.get(2)}
        );

        for (UUID[] pair : pairs) {
            Optional<CalcHubRouteDistance> route = distances.stream()
                .filter(d -> isPair(d, pair[0], pair[1]))
                .min(Comparator.comparingLong(d -> d.getDistance().getValue()));

            route.ifPresent(r -> addToGraph(graph, r.getStartHubId(), r.getEndHubId(), r.getDistance().getValue(), r.getTravelTime().getValue()));
        }
    }

    private void connectGeneralHubs(Map<UUID, Map<UUID, Long[]>> graph, List<Hub> hubs, List<UUID> mainHubs, List<CalcHubRouteDistance> distances, long maxDistance) {
        for (CalcHubRouteDistance d : distances) {
            UUID start = d.getStartHubId();
            UUID end = d.getEndHubId();
            long distance = d.getDistance().getValue();

            if (!mainHubs.contains(start) && !mainHubs.contains(end) && distance <= maxDistance) {
                addToGraph(graph, start, end, distance, d.getTravelTime().getValue());
            }
        }
    }

    private void connectNearbyMainHubs(Map<UUID, Map<UUID, Long[]>> graph, List<Hub> hubs, List<UUID> mainHubs, List<CalcHubRouteDistance> distances, long maxDistance) {
        for (Hub hub : hubs) {
            UUID hubId = hub.getId();
            if (mainHubs.contains(hubId)) continue;

            List<CalcHubRouteDistance> relatedRoutes = distances.stream()
                .filter(d -> isConnectedToMainHub(d, hubId, mainHubs))
                .collect(Collectors.toList());

            List<CalcHubRouteDistance> withinRange = relatedRoutes.stream()
                .filter(d -> d.getDistance().getValue() <= maxDistance)
                .collect(Collectors.toList());

            List<CalcHubRouteDistance> finalRoutes = withinRange.isEmpty()
                ? relatedRoutes.stream()
                .min(Comparator.comparingLong(d -> d.getDistance().getValue()))
                .map(List::of)
                .orElse(List.of())
                : withinRange;

            for (CalcHubRouteDistance r : finalRoutes) {
                UUID target = r.getStartHubId().equals(hubId) ? r.getEndHubId() : r.getStartHubId();
                addToGraph(graph, hubId, target, r.getDistance().getValue(), r.getTravelTime().getValue());
            }
        }
    }

    private void saveHubRoutes(Map<UUID, Map<UUID, Long[]>> graph) {
        for (UUID start : graph.keySet()) {
            for (UUID end : graph.get(start).keySet()) {
                if (!hubRouteRepository.existsByDepatureHubIdAndArrivalHubId(start, end)) {
                    Long[] route = graph.get(start).get(end);
                    String startName = hubRepository.findById(start).orElseThrow().getName().getValue();
                    String endName = hubRepository.findById(end).orElseThrow().getName().getValue();

                    HubRoute hubRoute = new HubRoute(route[1], route[0], start, startName, end, endName);
                    hubRouteRepository.save(hubRoute);
                }
            }
        }
    }

    private boolean isPair(CalcHubRouteDistance d, UUID a, UUID b) {
        return (d.getStartHubId().equals(a) && d.getEndHubId().equals(b)) ||
            (d.getStartHubId().equals(b) && d.getEndHubId().equals(a));
    }

    private boolean isConnectedToMainHub(CalcHubRouteDistance d, UUID hubId, List<UUID> mainHubs) {
        return (d.getStartHubId().equals(hubId) && mainHubs.contains(d.getEndHubId())) ||
            (d.getEndHubId().equals(hubId) && mainHubs.contains(d.getStartHubId()));
    }

    private void addToGraph(Map<UUID, Map<UUID, Long[]>> graph, UUID from, UUID to, long distance, long time) {
        graph.computeIfAbsent(from, k -> new HashMap<>()).put(to, new Long[]{distance, time});
        graph.computeIfAbsent(to, k -> new HashMap<>()).put(from, new Long[]{distance, time});
    }

}
