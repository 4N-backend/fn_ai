package com.fn.ai.delivery.presentation.dto;

import com.fn.ai.delivery.model.Delivery;
import java.util.UUID;

public record DeliveryArriveHubResponseDto(
    UUID deliveryId
) {

  public static DeliveryArriveHubResponseDto fromEntity(Delivery delivery) {
    return new DeliveryArriveHubResponseDto(delivery.getId());
  }
}
