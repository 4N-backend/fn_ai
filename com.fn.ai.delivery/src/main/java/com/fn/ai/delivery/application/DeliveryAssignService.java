package com.fn.ai.delivery.application;

import com.fn.ai.delivery.application.client.DeliveryManagerClient;
import com.fn.ai.delivery.application.client.dto.DeliveryManagerAssignResponseDto;
import com.fn.ai.delivery.model.Delivery;
import com.fn.ai.delivery.model.DeliveryRoute;
import com.fn.ai.delivery.model.repository.DeliveryRepository;
import com.fn.ai.delivery.model.vo.DeliverySequence;
import com.fn.ai.delivery.presentation.external.dto.DeliveryAssignResponseDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class DeliveryAssignService {

  private final DeliveryRepository deliveryRepository;
  private final DeliveryManagerClient deliveryManagerClient;

  @Transactional
  public DeliveryAssignResponseDto assign(UUID deliveryId, int sequence) {
    // 배송 조회
    Delivery delivery = deliveryRepository.findByIdWithRoutes(deliveryId)
        .orElseThrow();

    // 배정될 배송 루트 조회
    DeliveryRoute route = delivery.getRouteBySequence(new DeliverySequence(sequence));

    // 배정될 배송 담당자 조회
    DeliveryManagerAssignResponseDto deliveryManagerAssignResponseDto = deliveryManagerClient
        .getAssignedDeliveryManager(route.getDepartureHubId());

    route.assignDeliveryManager(deliveryManagerAssignResponseDto.deliveryManagerId());

    return DeliveryAssignResponseDto.of(delivery, deliveryManagerAssignResponseDto);
  }
}
