package com.fn.ai.order.application.dto;

import java.util.UUID;
import lombok.Builder;

@Builder
public record DeliveryCreateRequestDto(UUID orderId,
                                       UUID receiverHubId,
                                       UUID supplierHubId,
                                       String receiverHubAddress) {

  public static DeliveryCreateRequestDto of(
      UUID orderId,
      UUID receiverHubId,
      UUID supplierHubId,
      String receiverHubAddress) {
    return DeliveryCreateRequestDto.builder()
        .orderId(orderId)
        .receiverHubId(receiverHubId)
        .supplierHubId(supplierHubId)
        .receiverHubAddress(receiverHubAddress)
        .build();
  }
}
