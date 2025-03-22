package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.DeliveryManager;
import java.util.UUID;

public record DeliveryManagerAssignResponseDto(
    UUID deliveryManagerId,
    String slackId
) {

  public static DeliveryManagerAssignResponseDto of(DeliveryManager selected) {
    return new DeliveryManagerAssignResponseDto(
        selected.getId(),
        selected.getUser().getSlackId() == null
            ? null : selected.getUser().getSlackId().getValue());
  }
}
