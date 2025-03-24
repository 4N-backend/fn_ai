package com.fn.ai.user.presentation.dto;

import com.fn.ai.user.model.DeliveryManager;
import java.util.UUID;

public record DeliveryManagerNextSequenceResponseDto(
    UUID deliveryManagerId,
    String slackId,
    long sequence
) {

  public static DeliveryManagerNextSequenceResponseDto of(DeliveryManager selected) {
    return new DeliveryManagerNextSequenceResponseDto(
        selected.getId(),
        selected.getUser().getSlackId() == null
            ? null : selected.getUser().getSlackId().getValue(),
        selected.getDeliverySequence());
  }
}
