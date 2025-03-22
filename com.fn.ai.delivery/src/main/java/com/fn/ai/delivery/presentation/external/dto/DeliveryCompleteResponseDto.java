package com.fn.ai.delivery.presentation.external.dto;

import com.fn.ai.delivery.model.Delivery;
import java.util.UUID;

public record DeliveryCompleteResponseDto(
    UUID deliveryId
) {

  public static DeliveryCompleteResponseDto fromEntity(Delivery delivery) {
    return new DeliveryCompleteResponseDto(delivery.getId());
  }
}
