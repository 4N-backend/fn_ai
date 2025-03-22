package com.fn.ai.delivery.presentation.external.dto;

import com.fn.ai.delivery.model.Delivery;
import java.util.UUID;

public record DeliveryUpdateResponseDto(
    UUID deliveryId
) {

  public static DeliveryUpdateResponseDto fromEntity(Delivery delivery) {
    return new DeliveryUpdateResponseDto(delivery.getId());
  }
}
