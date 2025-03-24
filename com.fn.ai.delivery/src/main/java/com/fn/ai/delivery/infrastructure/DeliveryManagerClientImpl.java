package com.fn.ai.delivery.infrastructure;

import com.fn.ai.delivery.application.client.DeliveryManagerClient;
import com.fn.ai.delivery.application.client.dto.DeliveryManagerNextSequenceResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryManagerClientImpl implements DeliveryManagerClient {

  private final DeliveryManagerFeignClient deliveryManagerFeignClient;

  @Override
  public DeliveryManagerNextSequenceResponseDto getNextSequenceDeliveryManager(long lastSequence) {
    return deliveryManagerFeignClient.getNextSequenceDeliveryManager(lastSequence);
  }
}
