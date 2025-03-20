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
import com.fn.ai.hub.exception.HubNotFoundException;
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

    @Override
    @Transactional
    public HubRouteResponseDto createHubRoute(HubRouteCreateRequestDto requestDto) {

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        Hub departureHub = hubRepository.findById(departureHubId).orElseThrow(
            HubNotFoundException::new);
        Hub arrivalHub = hubRepository.findById(arrivalHubId)
            .orElseThrow(HubNotFoundException::new);

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
            HubNotFoundException::new);
        Hub arrivalHub = hubRepository.findById(arrivalHubId)
            .orElseThrow(HubNotFoundException::new);

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
        boolean isAsc, UUID keyword) {
        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));

        Page<HubRoute> hubRoutePage = hubRouteRepository.searchHubRoute(pageable, keyword);

        return hubRoutePage.map(HubRouteResponseDto::of);
    }


    @Override
    @Transactional
    public void generateHubRoutes() {
        final long MAX_DISTANCE_M = 50000; // 🚀 50km 이하만 저장 (단위: m)

        List<Hub> hubs = hubRepository.findAll();
        Map<UUID, Map<UUID, Long[]>> graph = new HashMap<>();

        // ✅ 1. 메인 노드 설정 (서울, 대전, 대구)
        UUID seoulHubId = hubRepository.findByName("서울특별시 센터").orElseThrow().getId();
        UUID daejeonHubId = hubRepository.findByName("대전광역시 센터").orElseThrow().getId();
        UUID daeguHubId = hubRepository.findByName("대구광역시 센터").orElseThrow().getId();

        List<UUID> mainHubs = List.of(seoulHubId, daejeonHubId, daeguHubId);

        // ✅ 2. DB에서 거리 정보 가져오기
        List<CalcHubRouteDistance> allDistances = calcHubRouteRepository.findAll();

        // 🚀 1) 메인 허브들끼리만 연결 (서울 ↔ 대전, 대전 ↔ 대구, 서울 ↔ 대구)
        List<UUID[]> mainHubPairs = List.of(
            new UUID[]{seoulHubId, daejeonHubId},
            new UUID[]{daejeonHubId, daeguHubId},
            new UUID[]{seoulHubId, daeguHubId}
        );

        for (UUID[] pair : mainHubPairs) {
            UUID hubA = pair[0];
            UUID hubB = pair[1];

            Optional<CalcHubRouteDistance> mainRoute = allDistances.stream()
                .filter(d -> (d.getStartHubId().equals(hubA) && d.getEndHubId().equals(hubB)) ||
                    (d.getStartHubId().equals(hubB) && d.getEndHubId().equals(hubA)))
                .min(Comparator.comparingLong(d -> d.getDistance().getValue()));

            if (mainRoute.isPresent()) {
                CalcHubRouteDistance route = mainRoute.get();
                long distance = route.getDistance().getValue();
                long travelTime = route.getTravelTime().getValue();

                graph.computeIfAbsent(hubA, k -> new HashMap<>())
                    .put(hubB, new Long[]{distance, travelTime});
                graph.computeIfAbsent(hubB, k -> new HashMap<>())
                    .put(hubA, new Long[]{distance, travelTime});

                log.info("✅ 메인 허브 연결됨: {} ↔ {}", hubA, hubB);
            }
        }

        // 🚀 2) 최대 거리(`MAX_DISTANCE_M`) 이하의 일반 허브들끼리 연결 (메인 허브 제외)
        for (CalcHubRouteDistance distanceData : allDistances) {
            UUID startHubId = distanceData.getStartHubId();
            UUID endHubId = distanceData.getEndHubId();
            long distance = distanceData.getDistance().getValue();
            long travelTime = distanceData.getTravelTime().getValue();

            // 🚀 메인 허브가 아닌 경우에만 연결
            if (!mainHubs.contains(startHubId) && !mainHubs.contains(endHubId)
                && distance <= MAX_DISTANCE_M) {
                graph.computeIfAbsent(startHubId, k -> new HashMap<>())
                    .put(endHubId, new Long[]{distance, travelTime});
                graph.computeIfAbsent(endHubId, k -> new HashMap<>())
                    .put(startHubId, new Long[]{distance, travelTime});
            }
        }

        // 🚀 3) 모든 일반 허브를 가장 가까운 메인 허브와 연결
        for (Hub hub : hubs) {
            UUID hubId = hub.getId();
            if (!mainHubs.contains(hubId)) { // 🚀 메인 허브가 아닌 경우만 처리
                Optional<CalcHubRouteDistance> nearestRoute = allDistances.stream()
                    .filter(
                        d -> (d.getStartHubId().equals(hubId) && mainHubs.contains(d.getEndHubId()))
                            ||
                            (d.getEndHubId().equals(hubId) && mainHubs.contains(d.getStartHubId())))
                    .min(Comparator.comparingLong(d -> d.getDistance().getValue()));

                if (nearestRoute.isPresent()) {
                    CalcHubRouteDistance route = nearestRoute.get();
                    UUID nearestHubId = route.getStartHubId().equals(hubId) ? route.getEndHubId()
                        : route.getStartHubId();
                    long distance = route.getDistance().getValue();
                    long travelTime = route.getTravelTime().getValue();

                    graph.computeIfAbsent(hubId, k -> new HashMap<>())
                        .put(nearestHubId, new Long[]{distance, travelTime});
                    graph.computeIfAbsent(nearestHubId, k -> new HashMap<>())
                        .put(hubId, new Long[]{distance, travelTime});

                    log.info("✅ 일반 허브가 메인 허브와 연결됨: {} ↔ {}", hubId, nearestHubId);
                }
            }
        }

        // 🚀 4) 최적 경로를 `HubRoute`에 저장
        for (UUID departureHubId : graph.keySet()) {
            for (UUID arrivalHubId : graph.get(departureHubId).keySet()) {
                boolean exists = hubRouteRepository.existsByDepatureHubIdAndArrivalHubId(
                    departureHubId, arrivalHubId);
                log.info("🔍 저장 여부 체크: {} → {} = {}", departureHubId, arrivalHubId, exists);

                if (!exists) {
                    Long[] routeData = graph.get(departureHubId).get(arrivalHubId);
                    String departureHubName = hubRepository.findById(departureHubId)
                        .orElseThrow().getName().getValue();
                    String arrivalHubName = hubRepository.findById(arrivalHubId).orElseThrow()
                        .getName().getValue();
                    HubRoute hubRoute = new HubRoute(routeData[1], routeData[0], departureHubId,
                        departureHubName, arrivalHubId, arrivalHubName);
                    hubRouteRepository.save(hubRoute);
                    log.info("✅ HubRoute 저장됨: {} → {} (거리: {}m)", departureHubId, arrivalHubId,
                        routeData[0]);
                }
            }
        }
    }

    /**
     * 미리 모든 허브 끼리의 소요시간과 거리 계산
     */
    @Override
    @Transactional
    public void calculateAllHubRoutes() {
        List<Hub> allHub = hubRepository.findAll();

        for (Hub startHub : allHub) {
            for (Hub endHub : allHub) {
                if (!startHub.equals(endHub)) {
                    String uri = mapApiService.createUri(startHub.getLocation(),
                        endHub.getLocation());
                    long[] responseApi = mapApiService.RequestApi(uri);

                    long distance = responseApi[0];
                    long travelTime = responseApi[1];
                    CalcHubRouteDistance calcHubDistance = new CalcHubRouteDistance(
                        startHub.getId(),
                        endHub.getId(), travelTime, distance);
                    calcHubRouteRepository.save(calcHubDistance);
                }
            }
        }

    }

    @Override
    public Queue<HubRouteResponseDto> findDeliveryRoute(HubRouteFindRequestDto requestDto) {
        Map<UUID, UUID> previous = new HashMap<>();
        PriorityQueue<ImmutablePair<Long, UUID>> pq = new PriorityQueue<>(
            Comparator.comparing(ImmutablePair::getLeft));
        Map<UUID, Long> distances = new HashMap<>();

        // 🚀 1. 저장된 `HubRoute` 데이터 조회
        List<HubRoute> routes = hubRouteRepository.findAll();

        // 🚀 2. 그래프 생성 (인접 리스트 방식)
        Map<UUID, Map<UUID, Long>> graph = new HashMap<>();
        for (HubRoute route : routes) {
            graph.computeIfAbsent(route.getDepartureHubId(), k -> new HashMap<>())
                .put(route.getArrivalHubId(), route.getDistance().getValue());
        }

        UUID departureHubId = requestDto.departureHubId();
        UUID arrivalHubId = requestDto.arrivalHubId();

        // 🚀 그래프 상태 출력
        log.info("✅ 그래프 데이터 확인: " + graph);
        log.info("✅ 출발지: " + departureHubId + ", 도착지: " + arrivalHubId);

        // 🚀 3. 초기 다익스트라 알고리즘 설정
        distances.put(departureHubId, 0L);
        pq.add(new ImmutablePair<>(0L, departureHubId));

        while (!pq.isEmpty()) {
            ImmutablePair<Long, UUID> current = pq.poll();
            UUID currentHubId = current.getRight();
            long currentDistance = current.getLeft();

            log.info("🛤️ 탐색 중: 현재 허브 = " + currentHubId + ", 거리 = " + currentDistance);

            // 🚀 이미 더 짧은 경로가 발견된 경우 스킵
            if (currentDistance > distances.getOrDefault(currentHubId, Long.MAX_VALUE)) {
                continue;
            }

            // 🚀 인접 허브 탐색
            for (Map.Entry<UUID, Long> entry : graph.getOrDefault(currentHubId, new HashMap<>())
                .entrySet()) {
                UUID neighborHubId = entry.getKey();
                long newDist = currentDistance + entry.getValue();

                if (newDist < distances.getOrDefault(neighborHubId, Long.MAX_VALUE)) {
                    distances.put(neighborHubId, newDist);
                    previous.put(neighborHubId, currentHubId); // 🚀 이전 허브 저장 (경로 추적용)
                    pq.add(new ImmutablePair<>(newDist, neighborHubId));
                }
            }
        }

        // 🚀 4. 최단 경로 추적 (출발지 → 경유지 → 도착지)
        Stack<HubRouteResponseDto> routeStack = new Stack<>();
        UUID currentHubId = arrivalHubId;

        while (previous.containsKey(currentHubId)) {
            UUID prevHubId = previous.get(currentHubId);

            log.info("🛤️ 경로 추적: " + prevHubId + " → " + currentHubId);

            hubRouteRepository.findByDepatureHubIdAndArrivalHubId(prevHubId, currentHubId)
                .map(HubRouteResponseDto::of)
                .ifPresent(routeStack::push);

            currentHubId = prevHubId;
        }

        // 🚀 5. 최단 경로를 `Queue`로 변환
        Queue<HubRouteResponseDto> deliveryRoute = new LinkedList<>();
        while (!routeStack.isEmpty()) {
            deliveryRoute.add(routeStack.pop());
        }

        // 🚀 6. 최단 경로가 존재하는 경우 출력
        if (deliveryRoute.isEmpty()) {
            log.info("⚠️ 최단 경로를 찾을 수 없습니다.");
        } else {
            log.info("✅ 최단 경로 찾기 완료! 경로:");
            for (HubRouteResponseDto route : deliveryRoute) {
                log.info("🔹 " + route.departureHubId() + " → " + route.arrivalHubId() + " (거리: "
                    + route.distance() + "m)");
            }
        }
        return deliveryRoute;
    }
}
