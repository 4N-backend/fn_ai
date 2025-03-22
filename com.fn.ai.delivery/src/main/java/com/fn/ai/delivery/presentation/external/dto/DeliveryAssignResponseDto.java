package com.fn.ai.delivery.presentation.external.dto;

import com.fn.ai.delivery.application.client.dto.DeliveryManagerAssignResponseDto;
import com.fn.ai.delivery.model.Delivery;
import java.util.UUID;

public record DeliveryAssignResponseDto(
    UUID deliveryId,
    UUID deliveryManagerId,
    String slackId
) {

  public static DeliveryAssignResponseDto of(Delivery delivery,
      DeliveryManagerAssignResponseDto responseDto) {
    return new DeliveryAssignResponseDto(delivery.getId(), responseDto.deliveryManagerId(),
        responseDto.slackId());
  }
}
