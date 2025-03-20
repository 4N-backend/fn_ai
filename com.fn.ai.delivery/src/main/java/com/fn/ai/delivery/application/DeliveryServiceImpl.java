package com.fn.ai.delivery.application;

import com.fn.ai.delivery.application.client.HubClient;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto;
import com.fn.ai.delivery.application.client.dto.HubRouteResponseDto.RouteInfo;
import com.fn.ai.delivery.exception.DeliveryNotFoundException;
import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.DeliveryRoute;
import com.fn.ai.delivery.model.repository.DeliveryRepository;
import com.fn.ai.delivery.presentation.dto.DeliveryArriveHubRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryArriveHubResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCompleteResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateRequestDto;
import com.fn.ai.delivery.presentation.dto.DeliveryCreateResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDepartHubResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliveryDetailsResponseDto;
import com.fn.ai.delivery.presentation.dto.DeliverySummaryResponseDto;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryServiceImpl implements DeliveryService {

  private final HubClient hubClient;
  private final DeliveryRepository deliveryRepository;

  @Override
  @Transactional
  public DeliveryCreateResponseDto create(DeliveryCreateRequestDto requestDto) {
    // 배송 생성
    Delivery delivery = requestDto.toEntity();

    // 배송 경로 조회
    HubRouteResponseDto hubRouteResponseDto = hubClient.getHubRoute(
        requestDto.departureHubId(), requestDto.arrivalHubId());

    // 배송 경로 기록 생성
    List<DeliveryRoute> routes = new ArrayList<>();
    int sequence = 0;
    for (RouteInfo routeInfo : hubRouteResponseDto.routeInfos()) {
      DeliveryRoute deliveryRoute = routeInfo.toEntity(sequence++);
      routes.add(deliveryRoute);
    }
    routes.forEach(delivery::addDeliveryRoute);

    Delivery savedDelivery = deliveryRepository.save(delivery);

    return DeliveryCreateResponseDto.fromEntity(savedDelivery);
  }

  @Override
  @Transactional(readOnly = true)
  public DeliveryDetailsResponseDto readOne(UUID deliveryId) {
    Delivery delivery = getDeliveryOrThrow(deliveryId);
    return DeliveryDetailsResponseDto.fromEntity(delivery);
  }

  @Override
  @Transactional(readOnly = true)
  public List<DeliverySummaryResponseDto> readAll() {
    List<Delivery> deliveryList = deliveryRepository.findAll();
    return deliveryList.stream().map(DeliverySummaryResponseDto::fromEntity).toList();
  }

  @Override
  @Transactional
  public void delete(UUID deliveryId) {
    Delivery delivery = getDeliveryOrThrow(deliveryId);
    delivery.delete();
  }

  @Override
  @Transactional
  public DeliveryDepartHubResponseDto departFromHub(UUID deliveryId, int sequence) {
    Delivery delivery = getDeliveryOrThrow(deliveryId);
    delivery.departFromHub(sequence);
    return DeliveryDepartHubResponseDto.fromEntity(delivery);
  }

  @Override
  @Transactional
  public DeliveryArriveHubResponseDto arriveToHub(
      UUID deliveryId, int sequence, DeliveryArriveHubRequestDto requestDto
  ) {
    Delivery delivery = getDeliveryOrThrow(deliveryId);
    delivery.arriveToHub(sequence, requestDto.distance(), requestDto.duration());
    return DeliveryArriveHubResponseDto.fromEntity(delivery);
  }

  @Override
  @Transactional
  public DeliveryCompleteResponseDto complete(UUID deliveryId) {
    Delivery delivery = getDeliveryOrThrow(deliveryId);
    delivery.completed();
    return DeliveryCompleteResponseDto.fromEntity(delivery);
  }

  private Delivery getDeliveryOrThrow(UUID deliveryId) {
    return deliveryRepository.findById(deliveryId).orElseThrow(DeliveryNotFoundException::new);
  }
}
