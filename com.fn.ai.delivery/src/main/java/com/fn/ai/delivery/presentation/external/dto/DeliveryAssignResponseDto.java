package com.fn.ai.delivery.presentation.external.dto;

import com.fn.ai.delivery.application.client.dto.DeliveryManagerNextSequenceResponseDto;
import com.fn.ai.delivery.model.Delivery;
import java.util.UUID;

public record DeliveryAssignResponseDto(
    UUID deliveryId,
    UUID deliveryManagerId,
    String slackId
) {

  public static DeliveryAssignResponseDto of(Delivery delivery,
      DeliveryManagerNextSequenceResponseDto responseDto) {
    return new DeliveryAssignResponseDto(delivery.getId(), responseDto.deliveryManagerId(),
        responseDto.slackId());
  }
}
