package com.fn.ai.delivery.presentation.dto;

import com.fn.ai.delivery.model.Delivery;
import java.util.UUID;

public record DeliveryDepartHubResponseDto(
    UUID deliveryId
) {

  public static DeliveryDepartHubResponseDto fromEntity(Delivery delivery) {
    return new DeliveryDepartHubResponseDto(delivery.getId());
  }
}
