package com.fn.ai.order.infrastructure.client;

import com.fn.ai.order.application.service.client.DeliveryClient;
import com.fn.ai.order.application.service.dto.DeliveryCreateRequestDto;
import com.fn.ai.order.application.service.dto.DeliveryCreateResponseDto;
import com.fn.ai.order.infrastructure.DeliveryFeignClient;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeliveryClientImpl implements DeliveryClient {

  private final DeliveryFeignClient deliveryFeignClient;


  @Override
  public Optional<DeliveryCreateResponseDto> createDelivery(DeliveryCreateRequestDto requestDto) {
    return deliveryFeignClient.createDelivery(requestDto);
  }
}
