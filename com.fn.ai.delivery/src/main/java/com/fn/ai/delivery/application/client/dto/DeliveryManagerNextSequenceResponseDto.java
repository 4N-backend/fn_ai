package com.fn.ai.delivery.application.client.dto;

import java.util.UUID;

public record DeliveryManagerNextSequenceResponseDto(
    UUID deliveryManagerId,
    String slackId,
    long sequence
) {

}
