package com.fn.ai.delivery.application;

import com.fn.ai.delivery.application.client.HubClient;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.DeliveryRoute;
import com.fn.ai.delivery.model.repository.DeliveryRepository;
import com.fn.ai.delivery.model.repository.DeliveryRouteRepository;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

  private final HubClient hubClient;
  private final DeliveryRepository deliveryRepository;
  private final DeliveryRouteRepository deliveryRouteRepository;

  @Transactional
  public DeliveryCreateResponseDto create(DeliveryCreateRequestDto requestDto) {
    // 배송 생성
    Delivery delivery = deliveryRepository.save(
        Delivery.of(
            requestDto.orderId(),
            requestDto.departureHubId(),
            requestDto.arrivalHubId(),
            requestDto.targetAddress()));

    // 배송 경로 조회
    HubRouteResponseDto hubRouteResponseDto = hubClient.getHubRoute(
        requestDto.departureHubId(), requestDto.arrivalHubId());

    // 배송 경로 기록 insert
    List<DeliveryRoute> routes = hubRouteResponseDto.routeStopInfos().stream()
        .map(routeStopInfo -> DeliveryRoute.of(
            delivery.getId(),
            routeStopInfo.departureHubId(),
            routeStopInfo.arrivalHubId(),
            routeStopInfo.distance(),
            routeStopInfo.travelTime(),
            routeStopInfo.sequence()
        )).toList();

    deliveryRouteRepository.saveAll(routes);

    return DeliveryCreateResponseDto.fromEntity(delivery);
  }
}
