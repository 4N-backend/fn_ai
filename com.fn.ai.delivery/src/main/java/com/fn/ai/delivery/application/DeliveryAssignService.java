package com.fn.ai.delivery.application;

import com.fn.ai.delivery.application.client.DeliveryManagerClient;
import com.fn.ai.delivery.application.client.dto.DeliveryManagerNextSequenceResponseDto;
import com.fn.ai.delivery.infrastructure.redis.CacheRepository;
import com.fn.ai.delivery.infrastructure.redis.DistributeLock;
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
  private final CacheRepository cacheRepository;

  @Transactional
  @DistributeLock(key = "#lastSequence")
  public DeliveryAssignResponseDto assign(UUID deliveryId, int sequence) {
    // 배송 조회
    Delivery delivery = deliveryRepository.findByIdWithRoutes(deliveryId)
        .orElseThrow();

    // 배정될 배송 루트 조회
    DeliveryRoute route = delivery.getRouteBySequence(new DeliverySequence(sequence));

    // 가장 최근에 배정된 sequence 조회
    long lastSequence = cacheRepository.getLastDeliveryManagerSequence();

    // 배정될 배송 담당자 조회
    DeliveryManagerNextSequenceResponseDto nextDeliveryManagerInfo = deliveryManagerClient
        .getNextSequenceDeliveryManager(lastSequence);

    // 배송 경로에 배송 담당자 배정
    route.assignDeliveryManager(nextDeliveryManagerInfo.deliveryManagerId());

    // 캐시 반영
    cacheRepository.setLastDeliveryManagerSequence(nextDeliveryManagerInfo.sequence());
    return DeliveryAssignResponseDto.of(delivery, nextDeliveryManagerInfo);
  }
}
